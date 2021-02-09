package com.dqpi.server.domain.net.modal

class ChannelUserInfo(
  val userId: String,
  val userNickName: String,
  val userHead: String,
  var status: Boolean
)

class ChannelUserReq(
  val pageStart: Int,
  val pageEnd: Int,
  val page: Int,
  val rows: Int,
  val userId: String,
  val userNickName: String
)

class NetServerInfo(
  val ip: String,
  val port: Int,
  val status: Boolean
)