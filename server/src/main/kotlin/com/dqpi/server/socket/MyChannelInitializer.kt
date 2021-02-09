package com.dqpi.server.socket

import codec.ObjDecoder
import codec.ObjEncoder
import com.dqpi.server.application.UserService
import com.dqpi.server.socket.handler.*
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

class MyChannelInitializer(private val userService: UserService): ChannelInitializer<SocketChannel>() {

  override fun initChannel(ch: SocketChannel) {
    ch.pipeline().let {
      //对象传输处理[解码]
      it.addLast(ObjDecoder())
      // 在管道中添加接收数据实现方法
      it.addLast(AddFriendHandler(userService))
      it.addLast(TalkDelHandler(userService))
      it.addLast(LoginHandler(userService))
      it.addLast(MsgGroupHandler(userService))
      it.addLast(MsgHandler(userService))
      it.addLast(ReconnectHandler(userService))
      it.addLast(SearchFriendHandler(userService))
      it.addLast(TalkNoticeHandler(userService))
      //对象传输处理[编码]
      it.addLast(ObjEncoder())
    }
  }
}