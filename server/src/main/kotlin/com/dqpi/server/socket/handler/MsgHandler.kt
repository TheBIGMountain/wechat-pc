package com.dqpi.server.socket.handler

import com.dqpi.server.application.UserService
import com.dqpi.server.infrastructure.common.Friend
import com.dqpi.server.infrastructure.common.toChannel
import com.dqpi.server.infrastructure.common.toChatRecordInfo
import com.dqpi.server.socket.MyBizHandler
import io.netty.channel.Channel
import protocol.msg.MsgReq
import protocol.msg.MsgRes
import reactor.kotlin.core.publisher.toMono

class MsgHandler(userService: UserService): MyBizHandler<MsgReq>(userService) {
  override fun channelRead(channel: Channel, msg: MsgReq) {
    // 异步写库
    userService.asyncAppendChatRecord(msg.toChatRecordInfo()).subscribe()
    // 添加对话框[如果对方没有你的对话框则添加]
    userService.addTalkBoxInfo(msg.friendId, msg.userId, Friend)
      // 获取好友通信管道
      .flatMap { msg.friendId.toChannel().toMono() }
      // 发送消息
      .doOnNext { it?.writeAndFlush(MsgRes(msg.userId, msg.msgText, msg.msgType, msg.msgDate)) }
      .subscribe()
  }
}