package com.dqpi.client.socket.handler

import com.dqpi.client.application.UIService
import com.dqpi.client.socket.MyBizHandler
import io.netty.channel.Channel
import javafx.application.Platform
import protocol.friend.AddFriendRes

class AddFriendHandler(uiService: UIService): MyBizHandler<AddFriendRes>(uiService) {
  override fun channelRead(channel: Channel, msg: AddFriendRes) {
    uiService.chat.let {
      Platform.runLater {
        it.addFriendUser(true, msg.friendId, msg.friendNickName, msg.friendHead)
      }
    }
  }
}