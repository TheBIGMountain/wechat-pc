package com.dqpi.server.socket.handler

import com.dqpi.server.application.UserService
import com.dqpi.server.infrastructure.common.toChannel
import com.dqpi.server.infrastructure.po.UserFriend
import com.dqpi.server.socket.MyBizHandler
import io.netty.channel.Channel
import protocol.friend.AddFriendReq
import protocol.friend.AddFriendRes
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

class AddFriendHandler(userService: UserService): MyBizHandler<AddFriendReq>(userService) {

  override fun channelRead(channel: Channel, msg: AddFriendReq) {
    // 1. 添加好友到数据库中[A->B B->A]
    userService.addUserFriend(Flux.just(
      UserFriend(userId = msg.userId, userFriendId = msg.friendId),
      UserFriend(userId = msg.friendId, userFriendId = msg.userId)
    // 2. 推送好友添加完成 A
    )).flatMap { userService.queryUserInfo(msg.friendId) }
      .doOnNext { channel.writeAndFlush(AddFriendRes(it.userId, it.userNickName, it.userHead)) }
      // 3. 推送好友添加完成 B
      .map { msg.friendId.toChannel()!! }
      .flatMap { userService.queryUserInfo(msg.friendId).zipWith(it.toMono()) }
      .doOnNext { it.t2.writeAndFlush(AddFriendRes(it.t1.userId, it.t1.userNickName, it.t1.userHead)) }
      .then(Unit.toMono())
      .onErrorResume { Mono.empty() }
      .subscribe()
  }
}