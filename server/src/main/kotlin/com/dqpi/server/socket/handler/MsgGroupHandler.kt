package com.dqpi.server.socket.handler

import com.dqpi.server.application.UserService
import com.dqpi.server.infrastructure.common.addChannelGroup
import com.dqpi.server.infrastructure.common.toChannelGroup
import com.dqpi.server.infrastructure.common.toChatRecordInfo
import com.dqpi.server.infrastructure.common.toMsgGroupRes
import com.dqpi.server.socket.MyBizHandler
import io.netty.channel.Channel
import protocol.msg.MsgGroupReq

class MsgGroupHandler(userService: UserService): MyBizHandler<MsgGroupReq>(userService) {
  override fun channelRead(channel: Channel, msg: MsgGroupReq) {
    val channelGroup = msg.talkId.let { it.toChannelGroup() ?: it.addChannelGroup(channel).let { _ -> it.toChannelGroup()!! } }
    // 异步写库
    userService.asyncAppendChatRecord(msg.toChatRecordInfo()).subscribe()
    // 群发消息
    userService.queryUserInfo(msg.userId)
      .toMsgGroupRes(msg)
      .doOnNext { channelGroup.writeAndFlush(it) }
      .subscribe()
  }
}

