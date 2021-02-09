package protocol.friend

import protocol.Packet
import protocol.SearchFriendRequest
import protocol.SearchFriendResponse
import protocol.friend.dto.UserDTO


class SearchFriendReq(
  val userId: String,
  val searchKey: String,
  override val command: Int = SearchFriendRequest
): Packet()

class SearchFriendRes(
  val list: List<UserDTO>? = null,
  override val command: Int = SearchFriendResponse
): Packet()