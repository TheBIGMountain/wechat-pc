package com.dqpi.client.event

import com.dqpi.client.infrastructure.addCache
import com.dqpi.client.infrastructure.channelCache
import com.dqpi.client.infrastructure.getCache
import com.dqpi.client.infrastructure.userIdCache
import com.dqpi.client.view.login.LoginEvent
import io.netty.channel.Channel
import protocol.login.LoginReq

class LoginEventImpl: LoginEvent {
  /**
   * 登陆验证
   * @param userId        用户ID
   * @param userPassword  用户密码
   */
  override fun doLoginCheck(userId: String, userPassword: String) {
    channelCache.getCache<Channel>().let {
      it.writeAndFlush(LoginReq(userId, userPassword))
      userIdCache.addCache(userId)
    }
  }
}