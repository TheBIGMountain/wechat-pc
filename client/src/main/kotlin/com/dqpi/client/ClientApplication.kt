package com.dqpi.client

import com.dqpi.client.application.UIService
import com.dqpi.client.event.ChatEventImpl
import com.dqpi.client.event.LoginEventImpl
import com.dqpi.client.infrastructure.config.NettyConfig
import com.dqpi.client.socket.NettyClient
import com.dqpi.client.view.chat.ChatController
import com.dqpi.client.view.login.LoginController
import javafx.application.Application
import javafx.stage.Stage
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import reactor.core.scheduler.Schedulers

@EnableConfigurationProperties(NettyConfig::class)
@SpringBootApplication
class ClientApplication: Application() {

  @Bean
  fun launch() = ApplicationRunner { launch(ClientApplication::class.java) }

  override fun start(primaryStage: Stage) {
    ChatController(ChatEventImpl()).apply {
      UIService(LoginController(LoginEventImpl(), this), this).apply {
        // 1.显示登陆窗口
        login.doShow()

        // 2. 启动netty客户端
        NettyClient(NettyConfig("127.0.0.1", 6666)).let {
          it.start(this)
            .subscribeOn(Schedulers.single())
            .doOnNext { _ -> while (!it.isActive()) Thread.sleep(500) }
            .subscribe()
        }
      }
    }
  }
}

fun main(args: Array<String>) {
  runApplication<ClientApplication>(*args)
}
