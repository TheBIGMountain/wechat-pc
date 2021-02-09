package com.dqpi.server.socket.handler

import com.dqpi.server.application.UserService
import com.dqpi.server.infrastructure.common.addChannel
import com.dqpi.server.infrastructure.common.addChannelGroup
import com.dqpi.server.infrastructure.common.removeChannel
import com.dqpi.server.socket.MyBizHandler
import io.netty.channel.Channel
import protocol.login.ReconnectReq

class ReconnectHandler(userService: UserService): MyBizHandler<ReconnectReq>(userService)  {
  override fun channelRead(channel: Channel, msg: ReconnectReq) {
    // 添加用户Channel
    msg.userId.removeChannel()
    msg.userId.addChannel(channel)
    // 添加群组Channel
    userService.queryTalkBoxGroupsIdList(msg.userId)
      .doOnNext { it.addChannelGroup(channel) }
      .subscribe()
  }
}