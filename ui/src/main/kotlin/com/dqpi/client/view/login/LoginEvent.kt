package com.dqpi.client.view.login


interface LoginEvent {
  /**
   * 登陆验证
   * @param userId        用户ID
   * @param userPassword  用户密码
   */
  fun doLoginCheck(userId: String, userPassword: String)
}