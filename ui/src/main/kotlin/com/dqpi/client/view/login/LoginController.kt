package com.dqpi.client.view.login

import com.dqpi.client.view.chat.ChatMethod

class LoginController(loginEvent: LoginEvent, private val chat: ChatMethod) : LoginInit(loginEvent), LoginMethod {
  private val loginView = LoginView(this, loginEvent)
  private val defaultLoginEvent = DefaultLoginEvent(this, loginEvent, this)

  override fun doShow() {
    super.show()
  }

  override fun doLoginError() {
    TODO("登录失败, 执行提示操作")
  }

  override fun doLoginSuccess() {
    // 关闭原窗口
    close()
    // 跳转
    chat.doShow()
  }
}
