package com.dqpi.server.socket.handler

import com.dqpi.server.application.UserService
import com.dqpi.server.infrastructure.common.toChannel
import com.dqpi.server.infrastructure.common.toTalkNoticeRes
import com.dqpi.server.socket.MyBizHandler
import io.netty.channel.Channel
import protocol.talk.TalkNoticeReq

class TalkNoticeHandler(userService: UserService): MyBizHandler<TalkNoticeReq>(userService) {
  override fun channelRead(channel: Channel, msg: TalkNoticeReq) {
    if (msg.talkType == 0) {
      // 1. 对话框数据落库
      userService.addTalkBoxInfo(msg.userId, msg.friendUserId, 0).subscribe()
      userService.addTalkBoxInfo(msg.friendUserId, msg.userId, 0).subscribe()
      // 2. 查询对话框信息[将自己发给好友的对话框中]
      userService.queryUserInfo(msg.userId)
        // 3. 发送对话框消息给好友
        .toTalkNoticeRes()
        // 获取好友通信管道
        .doOnNext { msg.friendUserId.toChannel()?.writeAndFlush(it) }
        .subscribe()
    }
    else userService.addTalkBoxInfo(msg.userId, msg.friendUserId, 1)
  }
}