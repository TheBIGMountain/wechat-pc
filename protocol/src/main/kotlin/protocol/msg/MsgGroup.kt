package protocol.msg

import protocol.MsgGroupRequest
import protocol.MsgGroupResponse
import protocol.Packet
import java.time.LocalDateTime

class MsgGroupReq(
  val talkId: String,
  val userId: String,
  val msgText: String,
  val msgType: Int,
  val msgDate: LocalDateTime = LocalDateTime.now(),
  override val command: Int = MsgGroupRequest
): Packet()

class MsgGroupRes(
  val talkId: String,
  val userId: String,
  val userNickName: String,
  val userHead: String,
  val msg: String,
  val msgType: Int,
  val msgDate: LocalDateTime = LocalDateTime.now(),
  override val command: Int = MsgGroupResponse
): Packet()