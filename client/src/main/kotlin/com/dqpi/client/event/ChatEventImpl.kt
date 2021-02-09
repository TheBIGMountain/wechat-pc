package com.dqpi.client.event

import com.dqpi.client.infrastructure.channelCache
import com.dqpi.client.infrastructure.getCache
import com.dqpi.client.view.chat.ChatEvent
import io.netty.channel.Channel
import javafx.scene.control.ListView
import javafx.scene.layout.Pane
import protocol.friend.AddFriendReq
import protocol.friend.SearchFriendReq
import protocol.msg.MsgGroupReq
import protocol.msg.MsgReq
import protocol.talk.TalkDelReq
import protocol.talk.TalkNoticeReq
import java.time.LocalDateTime

class ChatEventImpl: ChatEvent {
  /**
   * 聊天窗口退出操作
   */
  override fun doQuit() {
    channelCache.getCache<Channel>().close()
  }

  /**
   * 发送消息按钮
   *
   * @param userId   用户Id
   * @param talkId   对话Id(好友ID/群组ID)
   * @param talkType 对话框类型；0好友、1群组
   * @param msg      发送消息内容
   * @param msgDate  发送消息时间
   */
  override fun doSendMsg(userId: String, talkId: String, talkType: Int, msg: String, msgType: Int, msgDate: LocalDateTime) {
    channelCache.getCache<Channel>().let {
      // 好友0
      if (talkType == 0)
        it.writeAndFlush(MsgReq(userId, talkId, msg, msgType, msgDate))
      // 群组1
      else
        it.writeAndFlush(MsgGroupReq(talkId, userId, msg, msgType, msgDate))
    }
  }

  /**
   * 事件处理；开启与好友发送消息 [点击发送消息时候触发 -> 添加到对话框、选中、展示对话列表]
   *
   * @param userId       用户ID
   * @param userFriendId 好友ID
   */
  override fun doEventAddTalkUser(userId: String, userFriendId: String) {
    channelCache.getCache<Channel>().writeAndFlush(TalkNoticeReq(userId, userFriendId, 0))
  }

  /**
   * 事件处理；开启与群组发送消息
   *
   * @param userId  用户ID
   * @param groupId 群组ID
   */
  override fun doEventAddTalkGroup(userId: String, groupId: String) {
    channelCache.getCache<Channel>().writeAndFlush(TalkNoticeReq(userId, groupId, 0))
  }

  /**
   * 事件处理；删除指定对话框
   *
   * @param userId 用户ID
   * @param talkId 对话框ID
   */
  override fun doEventDelTalkUser(userId: String, talkId: String) {
    channelCache.getCache<Channel>().writeAndFlush(TalkDelReq(userId, talkId))
  }

  /**
   * 事件处理；查询有缘用户添加到列表
   *
   * @param userId   用户ID
   * @param listView 用户列表[非必需使用，同步接口可使用]
   */
  override fun addFriendLuck(userId: String, listView: ListView<Pane>) {
    channelCache.getCache<Channel>().writeAndFlush(SearchFriendReq(userId, ""))
  }

  /**
   * 事件处理；好友搜索[搜索后结果调用添加：addLuckFriend]
   *
   * @param userId 用户ID
   * @param text   搜索关键字
   */
  override fun doFriendLuckSearch(userId: String, text: String) {
    channelCache.getCache<Channel>().writeAndFlush(SearchFriendReq(userId, text))
  }

  /**
   * 添加好友事件
   *
   * @param userId   个人ID
   * @param friendId 好友ID
   */
  override fun doEventAddLuckUser(userId: String, friendId: String) {
    channelCache.getCache<Channel>().writeAndFlush(AddFriendReq(userId, friendId))
  }
}