package com.dqpi.server.socket.handler

import com.dqpi.server.application.UserService
import com.dqpi.server.socket.MyBizHandler
import io.netty.channel.Channel
import protocol.talk.TalkDelReq

class TalkDelHandler(userService: UserService): MyBizHandler<TalkDelReq>(userService) {
  override fun channelRead(channel: Channel, msg: TalkDelReq) {
    userService.deleteUserTalk(msg.userId, msg.talkId).subscribe()
  }
}