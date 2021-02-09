package com.dqpi.client.socket.handler

import com.dqpi.client.application.UIService
import com.dqpi.client.socket.MyBizHandler
import io.netty.channel.Channel
import javafx.application.Platform
import protocol.msg.MsgGroupRes

class MsgGroupHandler(uiService: UIService): MyBizHandler<MsgGroupRes>(uiService) {
  override fun channelRead(channel: Channel, msg: MsgGroupRes) {
    uiService.chat.let {
      Platform.runLater {
        it.addTalkMsgGroupLeft(msg.talkId, msg.userId, msg.userNickName, msg.userHead, msg.msg, msg.msgType, msg.msgDate, true, false, true)
      }
    }
  }
}