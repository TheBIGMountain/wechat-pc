package codec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import protocol.Packet
import utils.serialize

class ObjEncoder : MessageToByteEncoder<Packet>() {
  override fun encode(ctx: ChannelHandlerContext, packet: Packet, out: ByteBuf) {
    packet.serialize()
      .doOnNext { out.writeInt(it.size + 1) }
      .doOnNext { out.writeByte(packet.command) }
      .doOnNext { out.writeBytes(it) }
      .subscribe()
  }
}

