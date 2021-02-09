package protocol.login.dto

import java.time.LocalDateTime

class ChatRecordDTO(
  val talkId: String,
  val userId: String,
  val userNickName: String? = null,
  val userHead: String? = null,
  val msgUserType: Int,
  val msgContent: String,
  val msgType: Int,
  val msgDate: LocalDateTime = LocalDateTime.now()
)

class ChatTalkDTO(
 val talkId: String,
 val talkType: Int,
 val talkName: String,
 val talkHead: String,
 val talkSketch: String,
 val talkDate: LocalDateTime = LocalDateTime.now(),
 var chatRecordList: List<ChatRecordDTO>? = null
)

class GroupsDTO(
  val groupId: String,
  val groupName: String,
  val groupHead: String
)

class UserFriendDTO(
  val friendId: String,
  val friendName: String,
  val friendHead: String
)