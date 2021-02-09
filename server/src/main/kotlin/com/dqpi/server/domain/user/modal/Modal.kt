package com.dqpi.server.domain.user.modal

import java.time.LocalDateTime

class ChatRecordInfo(
  val userId: String,
  val friendId: String,
  val msgContent: String,
  val msgType: Int,
  val msgDate: LocalDateTime = LocalDateTime.now(),
  val talkType: Int
)

class GroupsInfo(
  val groupId: String,
  val groupName: String,
  val groupHead: String
)

class LuckUserInfo(
  val userId: String,
  val userNickName: String,
  val userHead: String,
  var status: Int
)

class TalkBoxInfo(
  val talkType: Int,
  val talkId: String,
  val talkName: String,
  val talkHead: String,
  val talkSketch: String,
  val talkDate: LocalDateTime = LocalDateTime.now()
)

class UserFriendInfo(
  val friendId: String,
  val friendName: String,
  val friendHead: String
)

class UserGroupInfo(
  val userId: String,
  val userNickName: String,
  val userHead: String
)

class UserInfo(
  val userId: String,
  val userNickName: String,
  val userHead: String
)