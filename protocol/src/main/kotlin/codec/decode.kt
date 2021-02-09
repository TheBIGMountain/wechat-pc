package codec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import protocol.toPacketType
import utils.deserialize

class ObjDecoder : ByteToMessageDecoder() {
  override fun decode(ctx: ChannelHandlerContext, byteBuf: ByteBuf, out: MutableList<Any>) {
    if (byteBuf.readableBytes() < 4) return
    byteBuf.markReaderIndex()
    val dataLength = byteBuf.readInt()
    if (byteBuf.readableBytes() < dataLength) {
      byteBuf.resetReaderIndex()
      return
    }
    val command = byteBuf.readByte().toInt()
    val data = ByteArray(dataLength - 1)
    byteBuf.readBytes(data)
    out.add(data.deserialize(command.toPacketType()!!))
  }
}


