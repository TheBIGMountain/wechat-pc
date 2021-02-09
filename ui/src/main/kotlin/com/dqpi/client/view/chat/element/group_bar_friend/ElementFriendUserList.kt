package com.dqpi.client.view.chat.element.group_bar_friend

import javafx.scene.control.ListView
import javafx.scene.layout.Pane

class ElementFriendUserList(
  val pane: Pane,
  val listView: ListView<Pane>
)

fun getElementFriendUserList(): ElementFriendUserList {
  val userListView = ListView<Pane>().apply {
    id = "userListView"
    setPrefSize(314.0, 0.0)
    layoutX = -10.0
    styleClass.add("elementFriendUser_listView")
  }

  return ElementFriendUserList(
    pane = Pane().apply {
      id = "friendUserList"
      setPrefSize(314.0, 0.0)
      layoutX = -10.0
      styleClass.add("elementFriendUserList")
      children.add(userListView)
    },
    listView = userListView
  )
}
