package protocol

import protocol.friend.AddFriendReq
import protocol.friend.AddFriendRes
import protocol.friend.SearchFriendReq
import protocol.friend.SearchFriendRes
import protocol.login.LoginReq
import protocol.login.LoginRes
import protocol.login.ReconnectReq
import protocol.msg.MsgGroupReq
import protocol.msg.MsgGroupRes
import protocol.msg.MsgReq
import protocol.msg.MsgRes
import protocol.talk.TalkDelReq
import protocol.talk.TalkNoticeReq
import protocol.talk.TalkNoticeRes

abstract class Packet {
  abstract val command: Int
  companion object {
    @JvmStatic
    val packetType = mapOf(
      LoginRequest to LoginReq::class.java,
      LoginResponse to LoginRes::class.java,
      MsgRequest to MsgReq::class.java,
      MsgResponse to MsgRes::class.java,
      TalkNoticeRequest to TalkNoticeReq::class.java,
      TalkNoticeResponse to TalkNoticeRes::class.java,
      SearchFriendRequest to SearchFriendReq::class.java,
      SearchFriendResponse to SearchFriendRes::class.java,
      AddFriendRequest to AddFriendReq::class.java,
      AddFriendResponse to AddFriendRes::class.java,
      DelTalkRequest to TalkDelReq::class.java,
      MsgGroupRequest to MsgGroupReq::class.java,
      MsgGroupResponse to MsgGroupRes::class.java,
      ReconnectRequest to ReconnectReq::class.java
    )
  }
}

fun Int.toPacketType() = Packet.packetType[this]