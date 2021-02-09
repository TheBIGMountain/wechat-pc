package com.dqpi.client.socket

import com.dqpi.client.application.UIService
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

abstract class MyBizHandler<T>(protected val uiService: UIService): SimpleChannelInboundHandler<T>() {

  abstract fun channelRead(channel: Channel, msg: T)

  override fun channelRead0(ctx: ChannelHandlerContext, msg: T) {
    channelRead(ctx.channel(), msg)
  }
}