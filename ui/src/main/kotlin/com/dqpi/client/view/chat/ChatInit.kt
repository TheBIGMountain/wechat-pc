package com.dqpi.client.view.chat

import com.dqpi.client.view.UIObject
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.StageStyle

abstract class ChatInit(private val chatEvent: ChatEvent): UIObject() {
  final override val root: Pane = FXMLLoader.load(javaClass.getResource("/fxml/chat/chat.fxml"))
  lateinit var userId: String
  lateinit var userNickName: String
  lateinit var userHead: String
  val txtInput = get<TextArea>("txt_input")

  init {
    scene = Scene(root).apply { fill = Color.TRANSPARENT }
    initStyle(StageStyle.TRANSPARENT)
    isResizable = false
    this.icons.add(Image("/fxml/chat/img/head/logo.png"))
  }
}