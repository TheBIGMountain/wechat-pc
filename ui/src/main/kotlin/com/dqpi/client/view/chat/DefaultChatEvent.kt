package com.dqpi.client.view.chat

import com.dqpi.client.view.chat.data.TalkBoxData
import com.dqpi.client.view.face.FaceController
import javafx.event.EventHandler
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.layout.Pane
import java.time.LocalDateTime
import java.util.*
import kotlin.system.exitProcess

class DefaultChatEvent(
  private val chatInit: ChatInit,
  private val chatEvent: ChatEvent,
  private val chatMethod: ChatMethod,
) {
  init {
    chatInit.move()
    min()
    quit()
    barChat()
    barFriend()
    doEventTextSend()
    doEventTouchSend()
    doEventToolFace();
  }

  // 最小化
  private fun min() {
    chatInit.get<Button>("group_bar_chat_min").setOnAction {
      chatInit.isIconified = true
    }
    chatInit.get<Button>("group_bar_friend_min").setOnAction {
      chatInit.isIconified = true
    }
  }

  // 退出
  private fun quit() {
    chatInit.get<Button>("group_bar_chat_close").setOnAction {
      chatEvent.doQuit()
      chatInit.close()
      exitProcess(0)
    }
    chatInit.get<Button>("group_bar_friend_close").setOnAction {
      chatEvent.doQuit()
      chatInit.close()
      exitProcess(0)
    }
  }

  // 发送消息事件[键盘]
  private fun doEventTextSend() {
    chatInit.get<TextArea>("txt_input").setOnKeyPressed {
      if (it.code == KeyCode.ENTER) doEventSendMsg()
    }
  }

  // 发送消息事件[按钮]
  private fun doEventTouchSend() {
    chatInit.get<Label>("touch_send").setOnMousePressed {
      doEventSendMsg()
    }
  }

  private fun doEventSendMsg() {
    val txtInput = chatInit.get<TextArea>("txt_input")
    val selectionModel = chatInit.get<ListView<Pane>>("talkList").selectionModel
    val selectedItem = selectionModel.selectedItem as Pane
    // 对话信息
    val talkBoxData = selectedItem.userData as TalkBoxData
    val msg = txtInput.text
    if (null == msg || "" == msg || "" == msg.trim { it <= ' ' }) {
      return
    }
    val msgDate = LocalDateTime.now()
    // 发送消息
    chatEvent.doSendMsg(chatInit.userId, talkBoxData.talkId, talkBoxData.talkType, msg, 0, msgDate)
    // 发送事件给自己添加消息
    chatMethod.addTalkMsgRight(talkBoxData.talkId, msg, 0, msgDate, true, true, false)
    txtInput.clear()
  }

  // 聊天
  private fun barChat() {
    chatInit.get<Button>("bar_chat").apply {
      setOnAction {
        switchBarChat(this, chatInit.get("group_bar_chat"), true)
        switchBarFriend(chatInit.get("bar_friend"), chatInit.get("group_bar_friend"), false)
      }
      setOnMouseEntered {
        if (isVisible) return@setOnMouseEntered
        else style = "-fx-background-image: url('/fxml/chat/img/system/chat_1.png')"
      }
      setOnMouseExited {
        if (isVisible) return@setOnMouseExited
        else style = "-fx-background-image: url('/fxml/chat/img/system/chat_0.png')"
      }
    }
  }

  // 好友
  private fun barFriend() {
    chatInit.get<Button>("bar_friend").apply {
      setOnAction {
        switchBarChat(chatInit.get("bar_chat"), chatInit.get("group_bar_chat"), false)
        switchBarFriend(this, chatInit.get("group_bar_friend"), true)
      }
      setOnMouseEntered {
        if (isVisible) return@setOnMouseEntered
        else style = "-fx-background-image: url('/fxml/chat/img/system/friend_1.png')"
      }
      setOnMouseExited {
        if (isVisible) return@setOnMouseExited
        else style = "-fx-background-image: url('/fxml/chat/img/system/friend_0.png')"
      }
    }
  }

  // 切换：bar_chat
  private fun switchBarChat(barChat: Button, groupBarChat: Pane, toggle: Boolean) {
    if (toggle) barChat.apply {
      style = "-fx-background-image: url('/fxml/chat/img/system/chat_2.png')"
      groupBarChat.isVisible = true
    }
    else barChat.apply {
      style = "-fx-background-image: url('/fxml/chat/img/system/chat_0.png')"
      groupBarChat.isVisible = false
    }
  }

  // 切换：bar_friend
  private fun switchBarFriend(barFriend: Button, groupBarFriend: Pane, toggle: Boolean) {
    if (toggle) barFriend.apply {
      style = "-fx-background-image: url('/fxml/chat/img/system/friend_2.png')"
      groupBarFriend.isVisible = true
    }
    else barFriend.apply {
      style = "-fx-background-image: url('/fxml/chat/img/system/friend_0.png')"
      groupBarFriend.isVisible = false
    }
  }

  // 好友；开启与好友发送消息 [点击发送消息时候触发 -> 添加到对话框、选中、展示对话列表]
  fun doEventOpenFriendUserSendMsg(
    sendMsgButton: Button, userFriendId: String,
    userFriendNickName: String, userFriendHead: String,
  ) {
    sendMsgButton.setOnAction {
      // 1. 添加好友到对话框
      chatMethod.addTalkBox(0, 0, userFriendId, userFriendNickName, userFriendHead, "", LocalDateTime.now(), true)
      // 2. 切换到对话框窗口
      switchBarChat(chatInit.get("bar_chat"), chatInit.get("group_bar_chat"), true)
      switchBarFriend(chatInit.get("bar_friend"), chatInit.get("group_bar_friend"), false)
      // 3. 事件处理；填充到对话框
      chatEvent.doEventAddTalkUser(chatInit.userId, userFriendId)
    }
  }

  // 群组；开启与群组发送消息
  fun doEventOpenFriendGroupSendMsg(
    sendMsgButton: Button, groupId: String,
    groupName: String, groupHead: String,
  ) {
    sendMsgButton.setOnAction {
      // 1. 添加好友到对话框
      chatMethod.addTalkBox(0, 1, groupId, groupName, groupHead, "", LocalDateTime.now(), true)
      // 2. 切换到对话框窗口
      switchBarChat(chatInit.get("bar_chat"), chatInit.get("group_bar_chat"), true)
      switchBarFriend(chatInit.get("bar_friend"), chatInit.get("group_bar_friend"), false)
      // 3. 事件处理；填充到对话框
      chatEvent.doEventAddTalkGroup(chatInit.userId, groupId)
    }
  }

  // 表情
  private fun doEventToolFace() {
    val face = FaceController(chatInit, chatInit, chatEvent, chatMethod)
    val toolFace = chatInit.get<Button>("tool_face")
    toolFace.onMousePressed = EventHandler {
      face.doShowFace(chatMethod.getToolFaceX(), chatMethod.getToolFaceY())
    }
  }
}