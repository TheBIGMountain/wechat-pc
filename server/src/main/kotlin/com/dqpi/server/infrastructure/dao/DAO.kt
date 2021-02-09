package com.dqpi.server.infrastructure.dao

import com.dqpi.server.infrastructure.po.*
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ChatRecordDao: ReactiveCrudRepository<ChatRecord, Long> {
  fun findAllByTalkTypeOrderByCreateTimeDesc(talkType: Int): Flux<ChatRecord>
}

interface UserGroupDao: ReactiveCrudRepository<UserGroup, Long> {
  fun findAllByUserId(userId: String): Flux<UserGroup>
}

interface UserDao: ReactiveCrudRepository<User, Long> {
  fun findByUserId(userId: String): Mono<User>
  fun findAllByUserIdLikeOrUserNickNameLike(userId: String, userNickName: String): Flux<User>
  fun countByUserIdAndUserNickNameLike(userId: String, userNickName: String): Mono<Long>
  fun findAllByUserIdAndUserNickNameLike(userId: String, userNickName: String): Flux<User>
}

interface TalkBoxDao: ReactiveCrudRepository<TalkBox, Long> {
  fun findAllByUserId(userId: String): Flux<TalkBox>
  fun findByUserIdAndTalkId(userId: String, talkId: String): Mono<TalkBox>
  fun deleteByUserIdAndTalkId(userId: String, talkId: String): Mono<Unit>
}

interface GroupsDao: ReactiveCrudRepository<Groups, Long> {
  fun findByGroupId(groupId: String): Mono<Groups>
}

interface UserFriendDao: ReactiveCrudRepository<UserFriend, Long> {
  fun findAllByUserId(userId: String): Flux<UserFriend>
  fun findByUserIdAndUserFriendId(userId: String, userFriendId: String): Mono<UserFriend>
}