package com.dqpi.client.view.login

import kotlin.system.exitProcess

/**
 * @author TheBIGMountain
 */
class DefaultLoginEvent(
  private val loginInit: LoginInit,
  private val loginEvent: LoginEvent,
  private val loginMethod: LoginMethod,
) {

  init {
    loginInit.move()
    min()
    quit()
    doEventLogin()
  }

  // 最小化
  private fun min() {
    loginInit.min.setOnAction { loginInit.isIconified = true }
  }

  // 退出
  private fun quit() {
    loginInit.close.setOnAction {
      loginInit.close()
      exitProcess(0)
    }
  }

  // 登陆
  private fun doEventLogin() {
    loginInit.login.setOnAction {
      loginEvent.doLoginCheck(loginInit.userId.text, loginInit.userPsw.getText())
    }
  }
}