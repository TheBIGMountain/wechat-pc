package com.dqpi.client.view.chat.element.group_bar_chat

import com.dqpi.client.utils.getHeight
import com.dqpi.client.utils.getWidth
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.Pane

class ElementInfoBox(
  val pane: Pane,
  val head: Pane,
  val nikeName: Label,
  val infoContentArrow: Label,
  val infoContent: TextArea
)

fun String.left(userHead: String, msg: String, msgType: Int): ElementInfoBox {
  val pane = Pane().also {
    it.setPrefSize(500.0, 50 + msg.getHeight())
    it.styleClass.add("infoBoxElement")
  }

  // 头像
  val head = Pane().also {
    it.setPrefSize(50.0, 50.0)
    it.layoutX = 15.0
    it.layoutY = 15.0
    it.styleClass.add("box_head")
    it.style = "-fx-background-image: url('/fxml/chat/img/head/${userHead}.png')"
    pane.children.add(it)
  }

  // 昵称
  val nickName = Label().also {
    it.setPrefSize(450.0, 20.0)
    it.layoutX = 75.0
    it.layoutY = 5.0
    it.text = this
    it.styleClass.add("box_nikeName")
    pane.children.add(it)
  }

  // 箭头
  val infoContentArrow = Label().also {
    it.setPrefSize(5.0, 20.0)
    it.layoutX = 75.0
    it.layoutY = 30.0
    it.styleClass.add("box_infoContent_arrow")
    pane.children.add(it)
  }

  // 内容
  if (msgType == 0) {
    val infoContent = TextArea().also {
        it.setPrefSize(msg.getWidth(), msg.getHeight())
        it.layoutX = 80.0
        it.layoutY = 30.0
        it.isWrapText = true
        it.isEditable = false
        it.text = msg
        it.styleClass.add("box_infoContent_left")
        pane.children.add(it)
    }
    return ElementInfoBox(pane, head, nickName, infoContentArrow, infoContent)
  }
  else {
    pane.children.add(Label().also {
      it.setPrefSize(60.0, 40.0)
      it.layoutX = 80.0
      it.layoutY = 30.0
      it.style = """
        -fx-background-image: url('/fxml/face/img/${msg}.png');
        -fx-background-position: center center;
        -fx-background-repeat: no-repeat;
        -fx-background-color: #ffffff;
        -fx-border-width: 0 1px 1px 0;
        -fx-border-radius: 2px;
        -fx-background-radius: 2px;
      """.trimIndent()
    })
    return ElementInfoBox(pane, head, nickName, infoContentArrow, TextArea().apply { isVisible = false })
  }
}

fun String.right(userHead: String, msg: String, msgType: Int): ElementInfoBox {
  val pane = Pane().also {
    it.setPrefSize(500.0, 50 + msg.getHeight())
    it.layoutX = 853.0
    it.layoutY = 0.0
    it.styleClass.add("infoBoxElement")
  }

  // 头像
  val head = Pane().also {
    it.setPrefSize(50.0, 50.0)
    it.layoutX = 770.0
    it.layoutY = 15.0
    it.styleClass.add("box_head")
    it.style = "-fx-background-image: url('/fxml/chat/img/head/${userHead}.png')"
    pane.children.add(it)
  }

  // 昵称
  val nickName = Label().apply { isVisible = false }

  // 箭头
  val infoContentArrow = Label().also {
    it.setPrefSize(5.0, 20.0)
    it.layoutX = 755.0
    it.layoutY = 30.0
    it.styleClass.add("box_infoContent_arrow")
    pane.children.add(it)
  }

  // 内容
  if (msgType == 0) {
    val infoContent = TextArea().also {
      it.setPrefSize(msg.getWidth(), msg.getHeight())
      it.layoutX = 755 - msg.getWidth()
      it.layoutY = 15.0
      it.isWrapText = true
      it.isEditable = false
      it.text = msg
      it.styleClass.add("box_infoContent_right")
      pane.children.add(it)
    }
    return ElementInfoBox(pane, head, nickName, infoContentArrow, infoContent)
  }
  else {
    pane.children.add(Label().also {
      it.setPrefSize(60.0, 40.0)
      it.layoutX = 755 - 60.0
      it.layoutY = 15.0
      it.style = """
        -fx-background-image: url('/fxml/face/img/${msg}.png');
        -fx-background-position: center center;
        -fx-background-repeat: no-repeat;
        -fx-background-color: #9eea6a;
        -fx-border-width: 0 1px 1px 0;
        -fx-border-radius: 2px;
        -fx-background-radius: 2px;
      """.trimIndent()
    })
    return ElementInfoBox(pane, head, nickName, infoContentArrow, TextArea().apply { isVisible = false })
  }
}