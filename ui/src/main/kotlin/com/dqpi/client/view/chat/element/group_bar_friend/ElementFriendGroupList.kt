package com.dqpi.client.view.chat.element.group_bar_friend

import javafx.scene.control.ListView
import javafx.scene.layout.Pane

class ElementFriendGroupList(
  val pane: Pane,
  val groupListView: ListView<Pane>
)

fun getElementFriendGroupList(): ElementFriendGroupList {
  val groupListView = ListView<Pane>().apply {
    id = "groupListView"
    setPrefSize(314.0, 0.0)
    layoutX = -10.0
    styleClass.add("elementFriendGroupList_listView")
  }

  return ElementFriendGroupList(
    pane = Pane().apply {
      id = "friendGroupList"
      setPrefSize(314.0, 0.0)
      layoutX = -10.0
      styleClass.add("elementFriendGroupList")
      children.add(groupListView)
    },
    groupListView = groupListView
  )
}