package com.dqpi.client.socket.handler

import com.dqpi.client.application.UIService
import com.dqpi.client.socket.MyBizHandler
import io.netty.channel.Channel
import javafx.application.Platform
import protocol.friend.SearchFriendRes

class SearchFriendHandler(uiService: UIService): MyBizHandler<SearchFriendRes>(uiService) {
  override fun channelRead(channel: Channel, msg: SearchFriendRes) {
    msg.list.let {
      if (it != null && it.isNotEmpty()) {
        Platform.runLater {
          uiService.chat.let { chat ->
            it.forEach { user ->
              chat.addLuckFriend(user.userId, user.userNickName, user.userHead, user.status)
            }
          }
        }
      }
    }
  }
}