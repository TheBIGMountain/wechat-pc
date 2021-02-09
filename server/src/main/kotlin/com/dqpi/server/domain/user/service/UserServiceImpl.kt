package com.dqpi.server.domain.user.service

import com.dqpi.server.application.UserService
import com.dqpi.server.domain.user.modal.*
import com.dqpi.server.domain.user.repository.UserRepository
import com.dqpi.server.infrastructure.po.UserFriend
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class UserServiceImpl(private val userRepository: UserRepository): UserService {

  /**
   * 登陆校验
   *
   * @param userId       用户ID
   * @param userPassword 用户密码
   * @return 登陆状态
   */
  override fun checkAuth(userId: String, userPassword: String): Mono<Boolean> {
    return userRepository.queryUserPassword(userId).map { it == userPassword }
      .defaultIfEmpty(false)
  }

  /**
   * 查询用户信息
   *
   * @param userId 用户ID
   * @return 用户信息
   */
  override fun queryUserInfo(userId: String): Mono<UserInfo> {
    return userRepository.queryUserInfo(userId)
  }

  /**
   * 查询个人用户对话框列表
   *
   * @param userId 个人用户ID
   * @return 对话框列表
   */
  override fun queryTalkBoxInfoList(userId: String): Flux<TalkBoxInfo> {
    return userRepository.queryTalkBoxInfoList(userId)
  }

  /**
   * 添加对话框
   *
   * @param userId   用户ID
   * @param talkId   好友ID
   * @param talkType 对话框类型[0好友、1群组]
   */
  override fun addTalkBoxInfo(userId: String, talkId: String, talkType: Int): Mono<Unit> {
    return userRepository.addTalkBoxInfo(userId, talkId, talkType)
  }

  /**
   * 查询个人用户好友列表
   *
   * @param userId 个人用户ID
   * @return 好友列表
   */
  override fun queryUserFriendInfoList(userId: String): Flux<UserFriendInfo> {
    return userRepository.queryUserFriendInfoList(userId)
  }

  /**
   * 查询个人用户群组列表
   *
   * @param userId 个人用户ID
   * @return 群组列表
   */
  override fun queryUserGroupInfoList(userId: String): Flux<GroupsInfo> {
    return userRepository.queryUserGroupInfoList(userId)
  }

  /**
   * 模糊查询用户
   *
   * @param userId    用户ID
   * @param searchKey 用户名、用户ID
   * @return < 10个用户集合
   */
  override fun queryFuzzyUserInfoList(userId: String, searchKey: String): Flux<LuckUserInfo> {
    return userRepository.queryFuzzyUserInfoList(userId, searchKey)
  }

  /**
   * 添加好友到数据库中
   *
   * @param userFriendList 好友集合
   */
  override fun addUserFriend(userFriendList: Flux<UserFriend>): Mono<Unit> {
    return userRepository.addUserFriend(userFriendList)
  }

  /**
   * 异步添加聊天记录
   *
   * @param chatRecordInfo 聊天记录信息
   */
  override fun asyncAppendChatRecord(chatRecordInfo: ChatRecordInfo): Mono<Unit> {
    return userRepository.appendChatRecord(chatRecordInfo).subscribeOn(Schedulers.single())
  }

  /**
   * 查询聊天记录
   *
   * @param talkId   对话框ID
   * @param userId   好友ID
   * @param talkType 对话类型；0好友、1群组
   * @return 聊天记录(10条)
   */
  override fun queryChatRecordInfoList(talkId: String, userId: String, talkType: Int): Flux<ChatRecordInfo> {
    return userRepository.queryChatRecordInfoList(talkId, userId, talkType)
  }

  /**
   * 删除用户对话框
   *
   * @param userId 用户ID
   * @param talkId 对话框ID
   */
  override fun deleteUserTalk(userId: String, talkId: String): Mono<Unit> {
    return userRepository.deleteUserTalk(userId, talkId)
  }

  /**
   * 查询用户群组ID集合
   *
   * @param userId 用户ID
   * @return 用户群组ID集合
   */
  override fun queryUserGroupsIdList(userId: String): Flux<String> {
    return userRepository.queryUserGroupsIdList(userId)
  }

  /**
   * 查询用户群组对话框
   *
   * @param userId 用户Id
   * @return       群组Id
   */
  override fun queryTalkBoxGroupsIdList(userId: String): Flux<String> {
    return userRepository.queryTalkBoxGroupsIdList(userId)
  }
}

