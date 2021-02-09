package com.dqpi.client.view.chat.data

class GroupsData(
  val groupId: String,
  val groupName: String,
  val groupHead: String
)

class RemindCount(
  val count: Int = 0
)

class TalkBoxData(
  val talkId: String,
  val talkType: Int,
  val talkName: String,
  val talkHead: String
)

class TalkData(
  val talkName: String,
  val talkHead: String
)