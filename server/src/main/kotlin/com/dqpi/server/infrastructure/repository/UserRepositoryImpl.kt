package com.dqpi.server.infrastructure.repository

import com.dqpi.server.domain.user.modal.*
import com.dqpi.server.domain.user.repository.UserRepository
import com.dqpi.server.infrastructure.common.*
import com.dqpi.server.infrastructure.dao.*
import com.dqpi.server.infrastructure.po.UserFriend
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono

@Service
class UserRepositoryImpl(
  private val userDao: UserDao,
  private val talkBoxDao: TalkBoxDao,
  private val groupsDao: GroupsDao,
  private val userFriendDao: UserFriendDao,
  private val userGroupDao: UserGroupDao,
  private val chatRecordDao: ChatRecordDao
): UserRepository {
  /**
   * 查询用户密码
   */
  override fun queryUserPassword(userId: String): Mono<String> {
    return userDao.findByUserId(userId).map { it.userPassword }
  }

  /**
   * 查询用户信息
   *
   * @param userId 用户ID
   * @return 用户信息
   */
  override fun queryUserInfo(userId: String): Mono<UserInfo> {
    return userDao.findByUserId(userId).toUserInfo()
  }

  /**
   * 查询个人用户对话框列表
   *
   * @param userId 个人用户ID
   * @return 对话框列表
   */
  override fun queryTalkBoxInfoList(userId: String): Flux<TalkBoxInfo> {
    return talkBoxDao.findAllByUserId(userId)
      .flatMap { talkBox ->
        if (talkBox.talkType == Friend)
          userDao.findByUserId(talkBox.talkId).toTalkBoxInfo(talkBox)
        else
          groupsDao.findByGroupId(talkBox.talkId).toTalkBoxInfo(talkBox)
      }
  }

  /**
   * 添加对话框
   *
   * @param userId   用户ID
   * @param talkId   好友ID
   * @param talkType 对话框类型[0好友、1群组]
   */
  override fun addTalkBoxInfo(userId: String, talkId: String, talkType: Int): Mono<Unit> {
    return talkBoxDao.findByUserIdAndTalkId(userId, talkId)
      .switchIfEmpty { talkBoxDao.save(userId.toTalkBox(talkId, talkType)) }
      .then(Unit.toMono())
  }

  /**
   * 查询个人用户好友列表
   *
   * @param userId 个人用户ID
   * @return 好友列表
   */
  override fun queryUserFriendInfoList(userId: String): Flux<UserFriendInfo> {
    return userFriendDao.findAllByUserId(userId)
      .flatMap { userDao.findByUserId(it.userFriendId).toUserFriendInfo() }
  }

  /**
   * 查询个人用户群组列表
   *
   * @param userId 个人用户ID
   * @return 群组列表
   */
  override fun queryUserGroupInfoList(userId: String): Flux<GroupsInfo> {
    return userGroupDao.findAllByUserId(userId)
      .flatMap { groupsDao.findByGroupId(it.groupId).toGroupsInfo() }
  }

  /**
   * 模糊查询用户
   *
   * @param userId    用户ID
   * @param searchKey 用户名、用户ID
   * @return < 10个用户集合
   */
  override fun queryFuzzyUserInfoList(userId: String, searchKey: String): Flux<LuckUserInfo> {
    return userDao.findAllByUserIdLikeOrUserNickNameLike("%${searchKey}%", "%${searchKey}%")
      .filter { it.userId != userId }
      .toLuckUserInfo()
      .doOnNext {
        userFriendDao.findByUserIdAndUserFriendId(userId, it.userId)
          .doOnNext { _ -> it.status = 2 }
          .subscribe()
      }.take(10)
  }

  /**
   * 添加好友到数据库中
   *
   * @param userFriendList 好友集合
   */
  override fun addUserFriend(userFriendList: Flux<UserFriend>): Mono<Unit> {
    return userFriendDao.saveAll(userFriendList).then(Unit.toMono())
  }

  /**
   * 添加聊天记录
   *
   * @param chatRecordInfo 聊天记录信息
   */
  override fun appendChatRecord(chatRecordInfo: ChatRecordInfo): Mono<Unit> {
    return chatRecordDao.save(chatRecordInfo.toChatRecord()).then(Unit.toMono())
  }

  /**
   * 查询聊天记录
   *
   * @param talkId   对话框ID
   * @param userId   好友ID
   * @param talkType 对话框类型；0好友、1群组
   * @return         聊天记录(10条)
   */
  override fun queryChatRecordInfoList(talkId: String, userId: String, talkType: Int): Flux<ChatRecordInfo> {
    val records = if (talkType == Friend)
      chatRecordDao.findAllByTalkTypeOrderByCreateTimeDesc(Friend)
        .filter { r -> arrayOf(userId, talkId).let { it.contains(r.userId) && it.contains(r.friendId) } }
    else
      chatRecordDao.findAllByTalkTypeOrderByCreateTimeDesc(Group).filter { it.friendId == talkId }
    return records.toChatRecordInfo()
  }

  /**
   * 删除用户对话框
   *
   * @param userId  用户ID
   * @param talkId  对话框ID
   */
  override fun deleteUserTalk(userId: String, talkId: String): Mono<Unit> {
    return talkBoxDao.deleteByUserIdAndTalkId(userId, talkId)
  }

  /**
   * 查询用户群组ID集合
   *
   * @param userId  用户ID
   * @return        用户群组ID集合
   */
  override fun queryUserGroupsIdList(userId: String): Flux<String> {
    return userGroupDao.findAllByUserId(userId).map { it.groupId }
  }

  /**
   * 查询用户群组对话框
   *
   * @param userId 用户Id
   * @return       群组Id
   */
  override fun queryTalkBoxGroupsIdList(userId: String): Flux<String> {
    return talkBoxDao.findAllByUserId(userId)
      .filter { it.talkType == Group }
      .map { it.talkId }
  }
}

