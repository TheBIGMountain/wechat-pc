package com.dqpi.client.socket.handler

import com.dqpi.client.application.UIService
import com.dqpi.client.socket.MyBizHandler
import io.netty.channel.Channel
import javafx.application.Platform
import protocol.msg.MsgRes

class MsgHandler(uiService: UIService): MyBizHandler<MsgRes>(uiService) {
  override fun channelRead(channel: Channel, msg: MsgRes) {
    uiService.chat.let {
      Platform.runLater {
        it.addTalkMsgUserLeft(msg.friendId, msg.msgText, msg.msgType, msg.msgDate, true, false, true)
      }
    }
  }
}