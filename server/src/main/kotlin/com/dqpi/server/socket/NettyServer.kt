package com.dqpi.server.socket

import com.dqpi.server.application.UserService
import com.dqpi.server.infrastructure.config.NettyConfig
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.net.InetSocketAddress

@Service
class NettyServer(
  private val userService: UserService,
  private val nettyConfig: NettyConfig,
) {
  private val parentGroup = NioEventLoopGroup(2)
  private val childGroup = NioEventLoopGroup()
  lateinit var channel: Channel

  fun start(): Mono<Channel> {
    return ServerBootstrap().apply {
      group(parentGroup, childGroup)
      channel(NioServerSocketChannel::class.java)
      option(ChannelOption.SO_BACKLOG, 128)
      childHandler(MyChannelInitializer(userService))
      channel = bind(InetSocketAddress(nettyConfig.port)).syncUninterruptibly().channel()
    }.toMono().map { channel }
  }

  fun destroy() {
    channel.close()
    parentGroup.shutdownGracefully()
    childGroup.shutdownGracefully()
  }
}