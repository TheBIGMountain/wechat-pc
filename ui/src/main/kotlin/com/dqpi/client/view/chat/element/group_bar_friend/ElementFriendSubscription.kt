package com.dqpi.client.view.chat.element.group_bar_friend

import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.text.TextAlignment


class ElementFriendSubscription(
  val pane: Pane,
  val head: Label,
  val name: Label,
  val subPane: Pane
)

fun getElementFriendSubscription(): ElementFriendSubscription {
  val pane = Pane().apply {
    setPrefSize(270.0, 70.0)
    styleClass.add("elementFriendSubscription")
  }

  // 头像区域
  val head = Label().apply {
    setPrefSize(50.0, 50.0)
    layoutX = 15.0
    layoutY = 10.0
    styleClass.add("elementFriendSubscription_head")
    pane.children.add(this)
  }

  // 名称区域
  val name = Label().apply {
    setPrefSize(200.0, 40.0)
    layoutX = 80.0
    layoutY = 15.0
    text = "公众号"
    styleClass.add("elementFriendSubscription_name")
    pane.children.add(this)
  }

  // 初始化未装载
  val subPane = Pane().apply {
    setPrefSize(850.0, 560.0)
    style = "-fx-background-color:transparent;"

    children.add(Button().apply {
      setPrefSize(65.0, 65.0)
      layoutX = 110.0
      layoutY = 30.0
      style = """
        -fx-background-color: transparent;
        -fx-background-radius: 0px;
        -fx-border-width: 50px;
        -fx-background-image: url('/fxml/login/img/system/bugstack_logo.png');
      """.trimIndent()
    })

    children.add(Label().apply {
      setPrefSize(150.0, 20.0)
      layoutX = 95.0
      layoutY = 100.0
      text = "bugstack虫洞栈"
      style = """
        -fx-background-color: transparent;
        -fx-border-width: 0;
        -fx-text-fill: #999999;
        -fx-font-size: 14px;
      """.trimIndent()
      textAlignment = TextAlignment.CENTER
    })
  }

  return ElementFriendSubscription(pane, head, name, subPane)
}