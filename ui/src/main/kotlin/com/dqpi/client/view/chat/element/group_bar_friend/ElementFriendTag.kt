package com.dqpi.client.view.chat.element.group_bar_friend

import javafx.scene.control.Button
import javafx.scene.layout.Pane

class ElementFriendTag(
  val pane: Pane
)

fun String.toElementFriendTag(): ElementFriendTag {
  return ElementFriendTag(Pane().also { pane ->
    pane.setPrefSize(270.0, 24.0)
    pane.style = "-fx-background-color: transparent;"

    pane.children.add(Button().also {
      it.setPrefSize(260.0, 24.0)
      it.layoutX = 5.0
      it.text = this
      it.styleClass.add("element_friend_tag")
    })
  })
}
