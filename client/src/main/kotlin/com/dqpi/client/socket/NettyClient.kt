package com.dqpi.client.socket

import com.dqpi.client.application.UIService
import com.dqpi.client.infrastructure.addCache
import com.dqpi.client.infrastructure.channelCache
import com.dqpi.client.infrastructure.config.NettyConfig
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

class NettyClient(private val nettyConfig: NettyConfig) {
  private val workerGroup = NioEventLoopGroup()
  lateinit var channel: Channel

  fun start(uiService: UIService): Mono<Channel> {
    return Bootstrap().apply {
      group(workerGroup)
      channel(NioSocketChannel::class.java)
      option(ChannelOption.AUTO_READ, true)
      handler(MyChannelInitializer(uiService))
      connect(nettyConfig.ip, nettyConfig.port).syncUninterruptibly().let {
        channel = it.channel()
        channelCache.addCache(channel)
      }
    }.toMono().map { channel }
  }

  fun destroy() {
    channel.close()
    workerGroup.shutdownGracefully()
  }

  fun isActive() = channel.isActive
}