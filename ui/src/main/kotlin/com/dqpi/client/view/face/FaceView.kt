package com.dqpi.client.view.face

import com.dqpi.client.view.chat.data.TalkBoxData
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.layout.Pane
import java.time.LocalDateTime

class FaceView(val faceInit: FaceInit)

fun FaceInit.toFaceView(): FaceView {
  val children = rootPane.children

  val f01 = Label()
  f01.userData = "f_01"
  f01.layoutX = 20.0
  f01.layoutY = 20.0
  f01.prefWidth = 30.0
  f01.prefHeight = 30.0
  f01.style = "-fx-background-image: url('/fxml/face/img/f_01.png')"
  f01.styleClass.add("look")

  val f02 = Label()
  f02.userData = "f_02"
  f02.layoutX = 60.0
  f02.layoutY = 20.0
  f02.prefWidth = 30.0
  f02.prefHeight = 30.0
  f02.style = "-fx-background-image: url('/fxml/face/img/f_02.png')"
  f02.styleClass.add("look")

  val f03 = Label()
  f03.userData = "f_03"
  f03.layoutX = 100.0
  f03.layoutY = 20.0
  f03.prefWidth = 30.0
  f03.prefHeight = 30.0
  f03.style = "-fx-background-image: url('/fxml/face/img/f_03.png')"
  f03.styleClass.add("look")

  val f04 = Label()
  f04.userData = "f_04"
  f04.layoutX = 140.0
  f04.layoutY = 20.0
  f04.prefWidth = 30.0
  f04.prefHeight = 30.0
  f04.style = "-fx-background-image: url('/fxml/face/img/f_04.png')"
  f04.styleClass.add("look")

  val f05 = Label()
  f05.userData = "f_05"
  f05.layoutX = 180.0
  f05.layoutY = 20.0
  f05.prefWidth = 30.0
  f05.prefHeight = 30.0
  f05.style = "-fx-background-image: url('/fxml/face/img/f_05.png')"
  f05.styleClass.add("look")

  val f11 = Label()
  f11.userData = "f_11"
  f11.layoutX = 20.0
  f11.layoutY = 70.0
  f11.prefWidth = 30.0
  f11.prefHeight = 30.0
  f11.style = "-fx-background-image: url('/fxml/face/img/f_11.png')"
  f11.styleClass.add("look")

  val f12 = Label()
  f12.userData = "f_12"
  f12.layoutX = 60.0
  f12.layoutY = 70.0
  f12.prefWidth = 30.0
  f12.prefHeight = 30.0
  f12.style = "-fx-background-image: url('/fxml/face/img/f_12.png')"
  f12.styleClass.add("look")

  val f13 = Label()
  f13.userData = "f_13"
  f13.layoutX = 100.0
  f13.layoutY = 70.0
  f13.prefWidth = 30.0
  f13.prefHeight = 30.0
  f13.style = "-fx-background-image: url('/fxml/face/img/f_13.png')"
  f13.styleClass.add("look")

  val f14 = Label()
  f14.userData = "f_14"
  f14.layoutX = 140.0
  f14.layoutY = 70.0
  f14.prefWidth = 30.0
  f14.prefHeight = 30.0
  f14.style = "-fx-background-image: url('/fxml/face/img/f_14.png')"
  f14.styleClass.add("look")

  val f15 = Label()
  f15.userData = "f_15"
  f15.layoutX = 180.0
  f15.layoutY = 70.0
  f15.prefWidth = 30.0
  f15.prefHeight = 30.0
  f15.style = "-fx-background-image: url('/fxml/face/img/f_15.png')"
  f15.styleClass.add("look")

  val f21 = Label()
  f21.userData = "f_21"
  f21.layoutX = 20.0
  f21.layoutY = 120.0
  f21.prefWidth = 30.0
  f21.prefHeight = 30.0
  f21.style = "-fx-background-image: url('/fxml/face/img/f_21.png')"
  f21.styleClass.add("look")

  val f22 = Label()
  f22.userData = "f_22"
  f22.layoutX = 60.0
  f22.layoutY = 120.0
  f22.prefWidth = 30.0
  f22.prefHeight = 30.0
  f22.style = "-fx-background-image: url('/fxml/face/img/f_22.png')"
  f22.styleClass.add("look")

  val f23 = Label()
  f23.userData = "f_23"
  f23.layoutX = 100.0
  f23.layoutY = 120.0
  f23.prefWidth = 30.0
  f23.prefHeight = 30.0
  f23.style = "-fx-background-image: url('/fxml/face/img/f_23.png')"
  f23.styleClass.add("look")

  val f24 = Label()
  f24.userData = "f_24"
  f24.layoutX = 140.0
  f24.layoutY = 120.0
  f24.prefWidth = 30.0
  f24.prefHeight = 30.0
  f24.style = "-fx-background-image: url('/fxml/face/img/f_24.png')"
  f24.styleClass.add("look")

  val f25 = Label()
  f25.userData = "f_25"
  f25.layoutX = 180.0
  f25.layoutY = 120.0
  f25.prefWidth = 30.0
  f25.prefHeight = 30.0
  f25.style = "-fx-background-image: url('/fxml/face/img/f_25.png')"
  f25.styleClass.add("look")

  children.addAll(f01, f02, f03, f04, f05, f11, f12, f13, f14, f15, f21, f22, f23, f24, f25)

  for (next in children) {
    next.onMouseClicked = EventHandler {
      val selectionModel = chatInit.get<ListView<Pane>>("talkList").selectionModel
      val selectedItem = selectionModel.selectedItem as Pane
      // 对话信息
      val talkBoxData = selectedItem.userData as TalkBoxData
      val msgDate = LocalDateTime.now()
      val faceId = next.userData as String
      chatMethod.addTalkMsgRight(talkBoxData.talkId, faceId, 1, msgDate, true, true, false)
      // 发送消息
      chatEvent.doSendMsg(chatInit.userId, talkBoxData.talkId,  talkBoxData.talkType, faceId, 1, msgDate)
    }
  }

  return FaceView(this)
}