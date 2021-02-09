package com.dqpi.client.socket

import codec.ObjDecoder
import codec.ObjEncoder
import com.dqpi.client.application.UIService
import com.dqpi.client.socket.handler.*
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

class MyChannelInitializer(private val uiService: UIService): ChannelInitializer<SocketChannel>() {
  override fun initChannel(ch: SocketChannel) {
    ch.pipeline().let {
      //对象传输处理[解码]
      it.addLast(ObjDecoder())
      // 在管道中添加接收数据实现方法
      it.addLast(AddFriendHandler(uiService))
      it.addLast(LoginHandler(uiService))
      it.addLast(MsgGroupHandler(uiService))
      it.addLast(MsgHandler(uiService))
      it.addLast(SearchFriendHandler(uiService))
      it.addLast(TalkNoticeHandler(uiService))
      //对象传输处理[编码]
      it.addLast(ObjEncoder())
    }
  }
}