package protocol.login

import protocol.Packet
import protocol.ReconnectRequest

class ReconnectReq(
  val userId: String,
  override val command: Int = ReconnectRequest
): Packet()