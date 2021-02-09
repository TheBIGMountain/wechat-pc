package protocol.friend

import protocol.AddFriendRequest
import protocol.AddFriendResponse
import protocol.Packet

class AddFriendReq(
  val userId: String,
  val friendId: String,
  override val command: Int = AddFriendRequest
): Packet()

class AddFriendRes(
  val friendId: String,
  val friendNickName: String,
  val friendHead: String,
  override val command: Int = AddFriendResponse
): Packet()


