package com.dqpi.client.view.face

import com.dqpi.client.view.UIObject
import com.dqpi.client.view.chat.ChatEvent
import com.dqpi.client.view.chat.ChatInit
import com.dqpi.client.view.chat.ChatMethod
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Modality
import javafx.stage.StageStyle

abstract class FaceInit(obj: UIObject): UIObject() {
  abstract val chatInit: ChatInit
  abstract val chatEvent: ChatEvent
  abstract val chatMethod: ChatMethod

  final override val root: Pane = FXMLLoader.load(javaClass.getResource("/fxml/face/face.fxml"))
  val rootPane = get<Pane>("face")

  init {
    scene = Scene(root).apply { fill = Color.TRANSPARENT }
    initStyle(StageStyle.TRANSPARENT)
    isResizable = false
    // 模态窗口
    initModality(Modality.APPLICATION_MODAL)
    initOwner(obj)
  }
}