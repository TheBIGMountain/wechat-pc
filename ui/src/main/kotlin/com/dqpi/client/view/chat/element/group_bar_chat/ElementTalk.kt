package com.dqpi.client.view.chat.element.group_bar_chat

import com.dqpi.client.utils.createInfoBoxListId
import com.dqpi.client.utils.createMsgDataId
import com.dqpi.client.utils.createTalkPaneId
import com.dqpi.client.utils.simpleDate
import com.dqpi.client.view.chat.data.RemindCount
import com.dqpi.client.view.chat.data.TalkBoxData
import com.dqpi.client.view.chat.data.TalkData
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.layout.Pane
import java.time.LocalDateTime


class ElementTalk(
  val pane: Pane,
  val head: Label,
  val nikeName: Label,
  val msgSketch: Label,
  val msgDate: Label,
  val msgRemind: Label,
  val delete: Button,
  val infoBoxList: ListView<Pane>,
)

fun ElementTalk.fillMsgSketch(talkSketch: String, talkDate: LocalDateTime) {
  msgSketch.text = if (talkSketch.length > 30)
    talkSketch.substring(0, 30)
  else talkSketch
  msgDate.text = talkDate.simpleDate()
}

fun String.getElementTalk(talkType: Int, talkName: String, talkHead: String,
                          talkSketch: String, talkDate: LocalDateTime): ElementTalk {
  val pane = Pane().also {
    it.id = createTalkPaneId()
    it.userData = TalkBoxData(this, talkType, talkName, talkHead)
    it.setPrefSize(270.0, 80.0)
    it.styleClass.add("talkElement")
  }

  // 头像区域
  val head = Label().also {
    it.setPrefSize(50.0, 50.0)
    it.layoutX = 15.0
    it.layoutY = 15.0
    it.styleClass.add("element_head")
    it.style = "-fx-background-image: url('/fxml/chat/img/head/${talkHead}.png')"
    pane.children.add(it)
  }

  // 昵称区域
  val nikeName = Label().also {
    it.setPrefSize(140.0, 25.0)
    it.layoutX = 80.0
    it.layoutY = 15.0
    it.text = talkName
    it.styleClass.add("element_nikeName")
    pane.children.add(it)
  }

  // 信息简述
  val msgSketch = Label().also {
    it.setPrefSize(200.0, 25.0)
    it.layoutX = 80.0
    it.layoutY = 40.0
    it.styleClass.add("element_msgSketch")
    it.text = if (talkSketch.length > 30) talkSketch.substring(0, 30) else talkSketch
    pane.children.add(it)
  }

  // 信息时间
  val msgDate = Label().also {
    it.id = createMsgDataId()
    it.setPrefSize(60.0, 25.0)
    it.layoutX = 220.0
    it.layoutY = 15.0
    it.styleClass.add("element_msgData")
    it.text = talkDate.simpleDate()
    pane.children.addAll(it)
  }

  // 消息提醒
  val msgRemind = Label().also {
    it.setPrefSize(15.0, 15.0)
    it.layoutX = 60.0
    it.layoutY = 5.0
    it.userData = RemindCount()
    it.text = ""
    it.isVisible = false
    it.styleClass.add("element_msgRemind")
    pane.children.add(it)
  }

  // 删除对话框按钮
  val delete = Button().also {
    it.setPrefSize(4.0, 4.0)
    it.isVisible = false
    it.layoutX = -8.0
    it.layoutY = 26.0
    it.styleClass.add("element_delete")
    pane.children.add(it)
  }

  // 消息框[初始化，未装载]，承载对话信息内容，点击按钮时候填充
  val infoBoxList = ListView<Pane>().also {
    it.id = createInfoBoxListId()
    it.userData = TalkData(talkName, talkHead)
    it.setPrefSize(850.0, 560.0)
    it.styleClass.add("infoBoxStyle")
  }

  return ElementTalk(pane, head, nikeName, msgSketch, msgDate, msgRemind, delete, infoBoxList)
}

