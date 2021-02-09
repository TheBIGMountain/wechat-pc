package com.dqpi.server

import com.dqpi.server.infrastructure.config.NettyConfig
import com.dqpi.server.socket.NettyServer
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.core.DatabaseClient

@EnableConfigurationProperties(NettyConfig::class)
@SpringBootApplication
class ServerApplication {
  @Bean
  fun init(databaseClient: DatabaseClient, nettyServer: NettyServer) = ApplicationRunner {
    // 执行sql语句
    ClassPathResource("sql.txt").inputStream.use {
      databaseClient.sql(it.readBytes().toString(Charsets.UTF_8)).then().subscribe()
    }

    // 启动netty
    nettyServer.start().subscribe()
  }
}

fun main(args: Array<String>) {
  runApplication<ServerApplication>(*args)
}
