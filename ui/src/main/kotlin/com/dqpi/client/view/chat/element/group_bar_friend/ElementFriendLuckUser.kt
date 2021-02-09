package com.dqpi.client.view.chat.element.group_bar_friend

import javafx.scene.control.Label
import javafx.scene.layout.Pane

class ElementFriendLuckUser(
  val pane: Pane,
  val id: Label,
  val head: Label,
  val name: Label,
  val status: Label,
  val line: Label,
)

fun String.toElementFriendLuckUser(userNickName: String, userHead: String, status: Int): ElementFriendLuckUser {
  val pane = Pane().also {
    it.userData = this
    it.setPrefSize(250.0, 70.0)
    it.styleClass.add("elementFriendLuckUser")
  }

  // 头像区域
  val head = Label().apply {
    setPrefSize(50.0, 50.0)
    layoutX = 125.0
    layoutY = 10.0
    styleClass.add("elementFriendLuckUser_head")
    style = "-fx-background-image: url('/fxml/chat/img/head/${userHead}.png')"
    pane.children.add(this)
  }

  // 名称区域
  val name = Label().apply {
    setPrefSize(200.0, 30.0)
    layoutX = 190.0
    layoutY = 10.0
    text = userNickName
    styleClass.add("elementFriendLuckUser_name")
    pane.children.add(this)
  }

  // ID区域
  val id = Label().also {
    it.setPrefSize(200.0, 20.0)
    it.layoutX = 190.0
    it.layoutY = 40.0
    it.text = this
    it.styleClass.add("elementFriendLuckUser_id")
    pane.children.add(it)
  }

  // 底线
  val line = Label().apply {
    setPrefSize(582.0, 1.0)
    layoutX = 125.0
    layoutY = 50.0
    styleClass.add("elementFriendLuck_line")
    pane.children.add(this)
  }

  // 状态区域
  val statusLabel = Label().apply {
    setPrefSize(56.0, 30.0)
    layoutX = 650.0
    layoutY = 20.0
    text = when(status) {
      1 -> "允许"
      2 -> "已添加"
      else -> "添加"
    }
    userData = status
    styleClass.add("elementFriendLuckUser_statusLabel_$status")
    pane.children.add(this)
  }

  return ElementFriendLuckUser(pane, id, head, name, statusLabel, line)
}
