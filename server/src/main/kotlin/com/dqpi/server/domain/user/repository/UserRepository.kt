package com.dqpi.server.domain.user.repository

import com.dqpi.server.domain.user.modal.*
import com.dqpi.server.infrastructure.po.UserFriend
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserRepository {
  /**
   * 查询用户密码
   */
  fun queryUserPassword(userId: String): Mono<String>

  /**
   * 查询用户信息
   *
   * @param userId 用户ID
   * @return 用户信息
   */
  fun queryUserInfo(userId: String): Mono<UserInfo>

  /**
   * 查询个人用户对话框列表
   *
   * @param userId 个人用户ID
   * @return 对话框列表
   */
  fun queryTalkBoxInfoList(userId: String): Flux<TalkBoxInfo>

  /**
   * 添加对话框
   *
   * @param userId   用户ID
   * @param talkId   好友ID
   * @param talkType 对话框类型[0好友、1群组]
   */
  fun addTalkBoxInfo(userId: String, talkId: String, talkType: Int): Mono<Unit>

  /**
   * 查询个人用户好友列表
   *
   * @param userId 个人用户ID
   * @return 好友列表
   */
  fun queryUserFriendInfoList(userId: String): Flux<UserFriendInfo>

  /**
   * 查询个人用户群组列表
   *
   * @param userId 个人用户ID
   * @return 群组列表
   */
  fun queryUserGroupInfoList(userId: String): Flux<GroupsInfo>


  /**
   * 模糊查询用户
   *
   * @param userId    用户ID
   * @param searchKey 用户名、用户ID
   * @return < 10个用户集合
   */
  fun queryFuzzyUserInfoList(userId: String, searchKey: String): Flux<LuckUserInfo>

  /**
   * 添加好友到数据库中
   *
   * @param userFriendList 好友集合
   */
  fun addUserFriend(userFriendList: Flux<UserFriend>): Mono<Unit>

  /**
   * 添加聊天记录
   *
   * @param chatRecordInfo 聊天记录信息
   */
  fun appendChatRecord(chatRecordInfo: ChatRecordInfo): Mono<Unit>

  /**
   * 查询聊天记录
   *
   * @param talkId   对话框ID
   * @param userId   好友ID
   * @param talkType 对话框类型；0好友、1群组
   * @return         聊天记录(10条)
   */
  fun queryChatRecordInfoList(talkId: String, userId: String, talkType: Int): Flux<ChatRecordInfo>

  /**
   * 删除用户对话框
   *
   * @param userId  用户ID
   * @param talkId  对话框ID
   */
  fun deleteUserTalk(userId: String, talkId: String): Mono<Unit>


  /**
   * 查询用户群组ID集合
   *
   * @param userId  用户ID
   * @return        用户群组ID集合
   */
  fun queryUserGroupsIdList(userId: String): Flux<String>

  /**
   * 查询用户群组对话框
   *
   * @param userId 用户Id
   * @return       群组Id
   */
  fun queryTalkBoxGroupsIdList(userId: String): Flux<String>

}