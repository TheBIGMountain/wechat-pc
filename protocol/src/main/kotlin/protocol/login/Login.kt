package protocol.login

import protocol.LoginRequest
import protocol.LoginResponse
import protocol.Packet
import protocol.login.dto.ChatTalkDTO
import protocol.login.dto.GroupsDTO
import protocol.login.dto.UserFriendDTO


class LoginReq(
  val userId: String,
  val userPsw: String,
  override val command: Int = LoginRequest
): Packet()

class LoginRes(
  var success: Boolean? = null,
  var userId: String? = null,
  var userHead: String? = null,
  var userNickName: String? = null,
  var chatTalkList: Collection<ChatTalkDTO> = emptyList(),
  var groupsList: Collection<GroupsDTO> = emptyList(),
  var userFriendList: Collection<UserFriendDTO> = emptyList(),
  override val command: Int = LoginResponse
): Packet()