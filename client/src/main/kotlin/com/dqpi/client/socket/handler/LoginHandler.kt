package com.dqpi.client.socket.handler

import com.dqpi.client.application.UIService
import com.dqpi.client.socket.MyBizHandler
import io.netty.channel.Channel
import javafx.application.Platform
import javafx.scene.control.Alert
import protocol.login.LoginRes

class LoginHandler(uiService: UIService): MyBizHandler<LoginRes>(uiService) {
  override fun channelRead(channel: Channel, msg: LoginRes) {
    Platform.runLater {
      if (!msg.success!!) {
        Alert(Alert.AlertType.INFORMATION).apply {
          titleProperty().set("信息")
          headerTextProperty().set("密码错误")
          showAndWait()
        }
      }
      else {
        uiService.login.doLoginSuccess()
        uiService.chat.let { chat ->
          // 设置用户信息
          chat.setUserInfo(msg.userId!!, msg.userNickName!!, msg.userHead!!)
          // 对话框
          msg.chatTalkList.forEach { talk ->
            chat.addTalkBox(0, talk.talkType, talk.talkId, talk.talkName, talk.talkHead, talk.talkSketch, talk.talkDate, true)
            // 好友
            talk.chatRecordList?.let {
              if (it.isNotEmpty() && talk.talkType == 0) {
                it.reversed().forEach { record ->
                  //  自己的消息
                  if (record.msgUserType == 0)
                    chat.addTalkMsgRight(record.talkId, record.msgContent, record.msgType, record.msgDate, true, false, false)
                  // 好友的消息
                  else
                    chat.addTalkMsgUserLeft(record.talkId, record.msgContent, record.msgType, record.msgDate, true, false, false)

                }
              }
              // 群组
              else if (it.isNotEmpty() && talk.talkType == 1) {
                it.reversed().forEach { record ->
                  // 自己的消息
                  if (record.msgUserType == 0)
                    chat.addTalkMsgRight(record.talkId, record.msgContent, record.msgType, record.msgDate, true, false, false)
                  // 他人的消息
                  else
                    chat.addTalkMsgGroupLeft(record.talkId, record.userId, record.userNickName!!, record.userHead!!, record.msgContent, record.msgType, record.msgDate, true, false, false)
                }
              }
            }
          }
          // 群组
          msg.groupsList.forEach {
            chat.addFriendGroup(it.groupId, it.groupName, it.groupHead)
          }
          // 好友
          msg.userFriendList.forEach {
            chat.addFriendUser(false, it.friendId, it.friendName, it.friendHead)
          }
        }
      }
    }
  }
}