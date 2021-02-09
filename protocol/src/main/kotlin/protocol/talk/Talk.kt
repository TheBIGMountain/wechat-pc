package protocol.talk

import protocol.DelTalkRequest
import protocol.Packet
import protocol.TalkNoticeRequest
import protocol.TalkNoticeResponse
import java.time.LocalDateTime


class TalkDelReq(
  val userId: String,
  val talkId: String,
  override val command: Int = DelTalkRequest
): Packet()

class TalkNoticeReq(
  val userId: String,
  val friendUserId: String,
  val talkType: Int,
  override val command: Int = TalkNoticeRequest
): Packet()

class TalkNoticeRes(
  val talkId: String,
  val talkName: String,
  val talkHead: String,
  val talkSketch: String,
  val talkDate: LocalDateTime,
  override val command: Int = TalkNoticeResponse
): Packet()