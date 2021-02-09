package com.dqpi.client.socket.handler

import com.dqpi.client.application.UIService
import com.dqpi.client.socket.MyBizHandler
import io.netty.channel.Channel
import javafx.application.Platform
import protocol.talk.TalkNoticeRes

class TalkNoticeHandler(uiService: UIService): MyBizHandler<TalkNoticeRes>(uiService) {
  override fun channelRead(channel: Channel, msg: TalkNoticeRes) {
    uiService.chat.let {
      Platform.runLater {
        it.addTalkBox(-1, 0, msg.talkId, msg.talkName, msg.talkHead, msg.talkSketch, msg.talkDate, false)
      }
    }
  }
}