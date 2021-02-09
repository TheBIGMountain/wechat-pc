package com.dqpi.client.view.chat

import javafx.scene.control.ListView
import javafx.scene.layout.Pane
import java.time.LocalDateTime

interface ChatEvent {
  /**
   * 聊天窗口退出操作
   */
  fun doQuit()

  /**
   * 发送消息按钮
   *
   * @param userId   用户Id
   * @param talkId   对话Id(好友ID/群组ID)
   * @param talkType 对话框类型；0好友、1群组
   * @param msg      发送消息内容
   * @param msgDate  发送消息时间
   */
  fun doSendMsg(userId: String, talkId: String, talkType: Int, msg: String, msgType: Int, msgDate: LocalDateTime)

  /**
   * 事件处理；开启与好友发送消息 [点击发送消息时候触发 -> 添加到对话框、选中、展示对话列表]
   *
   * @param userId       用户ID
   * @param userFriendId 好友ID
   */
  fun doEventAddTalkUser(userId: String, userFriendId: String)

  /**
   * 事件处理；开启与群组发送消息
   *
   * @param userId  用户ID
   * @param groupId 群组ID
   */
  fun doEventAddTalkGroup(userId: String, groupId: String)

  /**
   * 事件处理；删除指定对话框
   *
   * @param userId 用户ID
   * @param talkId 对话框ID
   */
  fun doEventDelTalkUser(userId: String, talkId: String)

  /**
   * 事件处理；查询有缘用户添加到列表
   *
   * @param userId   用户ID
   * @param listView 用户列表[非必需使用，同步接口可使用]
   */
  fun addFriendLuck(userId: String, listView: ListView<Pane>)

  /**
   * 事件处理；好友搜索[搜索后结果调用添加：addLuckFriend]
   *
   * @param userId 用户ID
   * @param text   搜索关键字
   */
  fun doFriendLuckSearch(userId: String, text: String)

  /**
   * 添加好友事件
   *
   * @param userId   个人ID
   * @param friendId 好友ID
   */
  fun doEventAddLuckUser(userId: String, friendId: String)
}