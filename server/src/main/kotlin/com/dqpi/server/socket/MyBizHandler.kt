package com.dqpi.server.socket

import com.dqpi.server.application.UserService
import com.dqpi.server.infrastructure.common.removeChannel
import com.dqpi.server.infrastructure.common.removeChannelGroup
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

abstract class MyBizHandler<T>(protected val userService: UserService) : SimpleChannelInboundHandler<T>() {

  abstract fun channelRead(channel: Channel, msg: T)

  override fun channelRead0(ctx: ChannelHandlerContext, msg: T) {
    channelRead(ctx.channel(), msg)
  }

  override fun channelInactive(ctx: ChannelHandlerContext) {
    super.channelInactive(ctx)
    "${ctx.channel().id()}".removeChannel()
    ctx.channel().removeChannelGroup()
  }

  override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
    "${ctx.channel().id()}".removeChannel()
    ctx.channel().removeChannelGroup()
  }
}