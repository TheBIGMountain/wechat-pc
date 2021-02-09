package com.dqpi.client.view.chat

import java.time.LocalDateTime
import java.util.*

/**
 * @author TheBIGMountain
 */
interface ChatMethod {
  /**
   * 打开窗口
   */
  fun doShow()

  /**
   * 设置登陆用户头像
   *
   * @param userId       用户ID
   * @param userNickName 用户昵称
   * @param userHead     头像图片名称
   */
  fun setUserInfo(userId: String, userNickName: String, userHead: String)

  /**
   * 填充对话框列表
   *
   * @param talkIdx    对话框位置；首位0、默认-1
   * @param talkType   对话框类型；好友0、群组1
   * @param talkId     对话框ID，1v1聊天ID、1vn聊天ID
   * @param talkName   对话框名称
   * @param talkHead   对话框头像
   * @param talkSketch 对话框通信简述(聊天内容最后一组信息)
   * @param talkDate   对话框通信时间
   * @param selected   选中[true/false]
   */
  fun addTalkBox(
    talkIdx: Int, talkType: Int, talkId: String, talkName: String,
    talkHead: String, talkSketch: String, talkDate: LocalDateTime, selected: Boolean,
  )

  /**
   * 填充对话框消息-好友[别人的消息]
   *
   * @param talkId   对话框ID[用户ID]
   * @param msg      消息
   * @param msgData  时间
   * @param idxFirst 是否设置首位
   * @param selected 是否选中
   * @param isRemind 是否提醒
   */
  fun addTalkMsgUserLeft(
    talkId: String, msg: String, msgType: Int, msgData: LocalDateTime,
    idxFirst: Boolean, selected: Boolean, isRemind: Boolean,
  )

  /**
   * 填充对话框消息-群组[别人的消息]
   *
   * @param talkId       对话框ID[群组ID]
   * @param userId       用户ID[群员]
   * @param userNickName 用户昵称
   * @param userHead     用户头像
   * @param msg          消息
   * @param msgDate      时间
   * @param idxFirst     是否设置首位
   * @param selected     是否选中
   * @param isRemind     是否提醒
   */
  fun addTalkMsgGroupLeft(
    talkId: String, userId: String,  userNickName: String,
    userHead: String, msg: String, msgType: Int, msgDate: LocalDateTime, idxFirst: Boolean,
    selected: Boolean, isRemind: Boolean,
  )

  /**
   * 填充对话框消息[自己的消息]
   *
   * @param talkId   对话框ID[用户ID]
   * @param msg      消息
   * @param msgData  时间
   * @param idxFirst 是否设置首位
   * @param selected 是否选中
   * @param isRemind 是否提醒
   */
  fun addTalkMsgRight(
    talkId: String, msg: String, msgType: Int, msgDate: LocalDateTime,
    idxFirst: Boolean, selected: Boolean, isRemind: Boolean,
  )

  /**
   * 好友列表添加‘群组’
   *
   * @param groupId   群组ID
   * @param groupName 群组名称
   * @param groupHead 群组头像
   */
  fun addFriendGroup(groupId: String, groupName: String, groupHead: String)

  /**
   * 好友列表添加‘用户’
   *
   * @param selected     选中;true/false
   * @param userId       好友ID
   * @param userNickName 好友昵称
   * @param userHead     好友头像
   */
  fun addFriendUser(selected: Boolean, userId: String, userNickName: String, userHead: String)

  /**
   * 缘分好友 | 默认添加10个好友
   *
   * @param userId       好友ID
   * @param userNickName 好友昵称
   * @param userHead     好友头像
   * @param status       状态；0添加、1允许、2已添加
   */
  fun addLuckFriend(userId: String, userNickName: String, userHead: String, status: Int)

  /**
   * 工具栏表情框体，位置：X
   */
  fun getToolFaceX(): Double

  /**
   * 工具栏表情框体，位置：Y
   */
  fun getToolFaceY(): Double
}