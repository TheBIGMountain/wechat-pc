package com.dqpi.client.view.chat.element.group_bar_friend

import javafx.scene.control.Label
import javafx.scene.layout.Pane


class ElementFriendUser(
  val pane: Pane,
  val headLabel: Label,
  val nameLabel: Label
)

fun String.toElementFriendUser(userNickName: String, userHead: String): ElementFriendUser {
  // 用户底板(存储用户ID)
  val pane = Pane().also {
    it.id = this
    it.setPrefSize(250.0, 70.0)
    it.styleClass.add("elementFriendUser")
  }

  // 头像区域
  val headLabel = Label().apply {
    setPrefSize(50.0, 50.0)
    layoutX = 15.0
    layoutY = 10.0
    styleClass.add("elementFriendUser_head")
    style = "-fx-background-image: url('/fxml/chat/img/head/${userHead}.png')"
    pane.children.add(this)
  }

  // 名称区域
  val nameLabel = Label().apply {
    setPrefSize(200.0, 40.0)
    layoutX = 80.0
    layoutY = 15.0
    text = userNickName
    styleClass.add("elementFriendUser_name")
    pane.children.add(this)
  }

  return ElementFriendUser(pane, headLabel, nameLabel)
}