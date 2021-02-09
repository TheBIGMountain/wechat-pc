package com.dqpi.client.view.login

import com.dqpi.client.view.UIObject
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.StageStyle

/**
 * @author TheBIGMountain
 */
abstract class LoginInit(private val loginEvent: LoginEvent): UIObject() {
  final override val root: Pane = FXMLLoader.load(javaClass.getResource("/fxml/login/login.fxml"))
  val min by lazy { get<Button>("login_min") }
  val close by lazy { get<Button>("login_close") }
  val login by lazy { get<Button>("login_button") }
  val userId by lazy { get<TextField>("userId") }
  val userPsw by lazy { get<PasswordField>("userPassword") }

  init {
    scene = Scene(root).apply { fill = Color.TRANSPARENT }
    initStyle(StageStyle.TRANSPARENT)
    isResizable = false
    icons.add(Image("/fxml/chat/img/head/logo.png"))
  }
}