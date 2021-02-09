package com.dqpi.server.infrastructure.po

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
class ChatRecord(
  @Id
  var id: Long? = null,
  var userId: String,
  var friendId: String,
  var msgContent: String,
  var msgType: Int,
  var msgDate: LocalDateTime = LocalDateTime.now(),
  var talkType: Int,
  var createTime: LocalDateTime = LocalDateTime.now(),
  var updateTime: LocalDateTime = LocalDateTime.now()
)

@Table
class Groups(
  @Id
  var id: Long? = null,
  var groupId: String,
  var groupName: String,
  var groupHead: String,
  var createTime: LocalDateTime = LocalDateTime.now(),
  var updateTime: LocalDateTime = LocalDateTime.now()
)

@Table
class TalkBox(
  @Id
  var id: Long? = null,
  var userId: String,
  var talkId: String,
  var talkType: Int,
  var createTime: LocalDateTime = LocalDateTime.now(),
  var updateTime: LocalDateTime = LocalDateTime.now()
)

@Table
class User(
  @Id
  var id: Long? = null,
  var userId: String,
  var userNickName: String,
  var userHead: String,
  var userPassword: String,
  var createTime: LocalDateTime = LocalDateTime.now(),
  var updateTime: LocalDateTime = LocalDateTime.now()
)

@Table
class UserFriend(
  @Id
  var id: Long? = null,
  var userId: String,
  var userFriendId: String,
  var createTime: LocalDateTime = LocalDateTime.now(),
  var updateTime: LocalDateTime = LocalDateTime.now()
)

@Table
class UserGroup(
  @Id
  var id: Long? = null,
  var userId: String,
  var groupId: String,
  var createTime: LocalDateTime = LocalDateTime.now(),
  var updateTime: LocalDateTime = LocalDateTime.now()
)