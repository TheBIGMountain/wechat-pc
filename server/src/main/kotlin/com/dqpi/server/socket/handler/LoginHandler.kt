package com.dqpi.server.socket.handler

import com.dqpi.server.application.UserService
import com.dqpi.server.infrastructure.common.*
import com.dqpi.server.socket.MyBizHandler
import io.netty.channel.Channel
import protocol.login.LoginReq
import protocol.login.LoginRes
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono
import java.util.concurrent.CountDownLatch

class LoginHandler(userService: UserService): MyBizHandler<LoginReq>(userService)  {
  override fun channelRead(channel: Channel, msg: LoginReq) {
    val downLatch = CountDownLatch(3)
    // 1. 登陆失败返回false
    userService.checkAuth(msg.userId, msg.userPsw)
      // 传输消息
      .doOnNext {
        if (!it) {
          channel.writeAndFlush(LoginRes(false))
          throw RuntimeException()
        }
      // 2. 登陆成功绑定Channel
      // 2.1 绑定用户ID
      }.doOnNext { msg.userId.addChannel(channel) }
      // 2.2 绑定群组
      .flatMapMany { userService.queryUserGroupsIdList(msg.userId) }
      .doOnNext { it.addChannel(channel) }
      // 3. 反馈消息；用户信息、用户对话框列表、好友列表、群组列表
      // 组装消息包
      // 3.1 用户信息
      .then(userService.queryUserInfo(msg.userId))
      .publishOn(Schedulers.single())
      .map { userInfo ->
        LoginRes().apply {
          // 3.2 对话框
          userService.queryTalkBoxInfoList(msg.userId)
            .publishOn(Schedulers.parallel())
            .map { it.toChatTalkDTO() }
            .doOnNext { chatTalkDTO ->
              // 好友；聊天消息
              val chatRecordDTOList = if (chatTalkDTO.talkType == Friend) {
                userService.queryChatRecordInfoList(chatTalkDTO.talkId, msg.userId, Friend)
                  .toChatRecordDTO(chatTalkDTO.talkId, msg.userId)
              }
              // 群组；聊天消息
              else {
                userService.queryChatRecordInfoList(chatTalkDTO.talkId, msg.userId, Group)
                  .flatMap { it.toMono().zipWith(userService.queryUserInfo(it.userId)) }
                  .toChatRecordDTO(chatTalkDTO.talkId, msg.userId)
              }
              chatRecordDTOList.collectList().doOnNext { chatTalkDTO.chatRecordList = it }.subscribe()
            }.collectList()
            .doOnNext { chatTalkList = it }
            .doOnNext { downLatch.countDown() }
            .subscribe()
          // 3.3 群组
          userService.queryUserGroupInfoList(msg.userId)
            .publishOn(Schedulers.parallel())
            .toGroupsDto()
            .collectList()
            .doOnNext { groupsList = it }
            .doOnNext { downLatch.countDown() }
            .subscribe()
          // 3.4 好友
          userService.queryUserFriendInfoList(msg.userId)
            .publishOn(Schedulers.parallel())
            .toUserFriendDTO()
            .collectList()
            .doOnNext { userFriendList = it }
            .doOnNext { downLatch.countDown() }
            .subscribe()
          success = true
          userId = userInfo.userId
          userNickName = userInfo.userNickName
          userHead = userInfo.userHead
        }
      // 等待执行完成
      }.doOnNext { downLatch.await() }
      // 传输消息
      .doOnNext { channel.writeAndFlush(it) }
      .onErrorResume { Mono.empty() }
      .subscribe()
  }
}


