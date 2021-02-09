package com.dqpi.client.view.login

interface LoginMethod {
  /**
   * 打开登陆窗口
   */
  fun doShow()

  /**
   * 登陆失败
   */
  fun doLoginError()

  /**
   * 登陆成功；跳转聊天窗口[关闭登陆窗口，打开新窗口]
   */
  fun doLoginSuccess()
}