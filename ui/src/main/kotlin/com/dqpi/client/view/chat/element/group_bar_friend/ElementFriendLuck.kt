package com.dqpi.client.view.chat.element.group_bar_friend

import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.Pane

class ElementFriendLuck(
  val pane: Pane,
  val head: Label,
  val name: Label,
  val friendLuckPane: Pane,
  val friendLuckSearch: TextField,
  val friendLuckListView: ListView<Pane>
)

fun getElementFriendLuck(): ElementFriendLuck {
  val pane = Pane().apply {
    id = "elementFriendLuck"
    setPrefSize(270.0, 70.0)
    styleClass.add("elementFriendLuck")
  }

  // 头像区域
  val head = Label().apply {
    setPrefSize(50.0, 50.0)
    layoutX = 15.0
    layoutY = 10.0
    styleClass.add("elementFriendLuck_head")
    pane.children.add(this)
  }

  // 名称区域
  val name = Label().apply {
    setPrefSize(200.0, 40.0)
    layoutX = 80.0
    layoutY = 15.0
    text = "新的朋友"
    styleClass.add("elementFriendLuck_name")
    pane.children.add(this)
  }

  // 初始化框体区域[搜索好友框、展现框]
  val friendLuckPane = Pane().apply {
    setPrefSize(850.0, 560.0)
    styleClass.add("friendLuckPane")
  }

  // 搜索好友框
  val friendLuckSearch = TextField().apply {
    setPrefSize(600.0, 50.0)
    layoutX = 125.0
    layoutY = 25.0
    promptText = "搜一搜"
    padding = Insets(10.0)
    styleClass.add("friendLuckSearch")
    friendLuckPane.children.add(this)
  }

  // 用户列表框[初始化，未装载]
  val friendLuckListView = ListView<Pane>().apply {
    id = "friendLuckListView"
    setPrefSize(850.0, 460.0)
    layoutY = 75.0
    styleClass.add("friendLuckListView")
    friendLuckPane.children.add(this)
  }

  return ElementFriendLuck(pane, head, name, friendLuckPane, friendLuckSearch, friendLuckListView)
}