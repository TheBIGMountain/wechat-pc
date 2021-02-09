package protocol.msg

import protocol.MsgRequest
import protocol.MsgResponse
import protocol.Packet
import java.time.LocalDateTime

class MsgReq(
  val userId: String,
  val friendId: String,
  val msgText: String,
  val msgType: Int,
  val msgDate: LocalDateTime = LocalDateTime.now(),
  override val command: Int = MsgRequest
): Packet()

class MsgRes(
  val friendId: String,
  val msgText: String,
  val msgType: Int,
  val msgDate: LocalDateTime = LocalDateTime.now(),
  override val command: Int = MsgResponse
): Packet()