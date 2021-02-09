package com.dqpi.client.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("netty")
class NettyConfig(
  val ip: String,
  val port: Int
)

