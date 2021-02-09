package com.dqpi.server.socket.handler

import com.dqpi.server.application.UserService
import com.dqpi.server.infrastructure.common.toUserDTO
import com.dqpi.server.socket.MyBizHandler
import io.netty.channel.Channel
import protocol.friend.SearchFriendReq
import protocol.friend.SearchFriendRes

class SearchFriendHandler(userService: UserService): MyBizHandler<SearchFriendReq>(userService) {
  override fun channelRead(channel: Channel, msg: SearchFriendReq) {
    userService.queryFuzzyUserInfoList(msg.userId, msg.searchKey)
      .toUserDTO()
      .collectList()
      .map { SearchFriendRes(it) }
      .doOnNext { channel.writeAndFlush(it) }
      .subscribe()
  }
}