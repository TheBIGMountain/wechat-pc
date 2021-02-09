package com.dqpi.client.view.chat

import com.dqpi.client.view.chat.data.RemindCount
import com.dqpi.client.view.chat.data.TalkBoxData
import com.dqpi.client.view.chat.element.group_bar_friend.*
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.input.KeyCode
import javafx.scene.layout.Pane


class ChatView(
  private val chatInit: ChatInit,
  val chatEvent: ChatEvent,
) {

  init {
    // 1. 好友列表添加工具方法‘新的朋友’
    initAddFriendLuck()
    // 2. 好友列表添加‘公众号’
    addFriendSubscription()
    // 3. 好友群组框体
    addFriendGroupList()
    // 4. 好友框体
    addFriendUserList()
  }

  /**
   * 好友列表添加工具方法‘新的朋友’
   */
  private fun initAddFriendLuck() {
    val friendList = chatInit.get<ListView<Pane>>("friendList")
    val items = friendList.items

    val elementFriendTag = "新的朋友".toElementFriendTag()
    items.add(elementFriendTag.pane)

    val element = getElementFriendLuck()
    val pane = element.pane
    items.add(pane)

    // 面板填充和事件
    pane.setOnMousePressed {
      val friendLuckPane = element.friendLuckPane
      setContentPaneBox("itstack-naive-chat-ui-chat-friend-luck", "新的朋友", friendLuckPane)
      chatInit.clearViewListSelectedAll(chatInit.get("userListView"), chatInit.get("groupListView"))
      element.friendLuckListView.items.clear()
      chatEvent.addFriendLuck(chatInit.userId, element.friendLuckListView)
    }

    // 搜索框事件
    element.friendLuckSearch.let { search ->
      search.setOnKeyPressed {
        if (it.code == KeyCode.ENTER) {
          var text = search.text
          if (text == null) text = ""
          if (text.length > 30) text = text.substring(0, 30)
          text = text.trim()
          chatEvent.doFriendLuckSearch(chatInit.userId, text)
          // 搜索清空元素
          element.friendLuckListView.items.clear()
        }
      }
    }
  }

  /**
   * 好友列表添加‘公众号’
   */
  private fun addFriendSubscription() {
    val friendList = chatInit.get<ListView<Pane>>("friendList")
    val items = friendList.items

    val elementFriendTag = "公众号".toElementFriendTag()
    items.add(elementFriendTag.pane)

    val element = getElementFriendSubscription()
    val pane = element.pane
    items.add(pane)

    pane.setOnMousePressed {
      chatInit.clearViewListSelectedAll(chatInit.get("userListView"), chatInit.get("groupListView"))
      setContentPaneBox("itstack-naive-chat-ui-chat-friend-subscription", "公众号", element.subPane)
    }
  }

  /**
   * 好友群组框体
   */
  private fun addFriendGroupList() {
    val friendList = chatInit.get<ListView<Pane>>("friendList")
    val items = friendList.items

    val elementFriendTag = "群聊".toElementFriendTag()
    items.add(elementFriendTag.pane)

    val element = getElementFriendGroupList()
    val pane = element.pane
    items.add(pane)
  }

  /**
   * 好友框体
   */
  private fun addFriendUserList() {
    val friendList = chatInit.get<ListView<Pane>>("friendList")
    val items = friendList.items

    val elementFriendTag = "好友".toElementFriendTag()
    items.add(elementFriendTag.pane)

    val element = getElementFriendUserList()
    val pane = element.pane
    items.add(pane)
  }

  /**
   * 更新对话框列表元素位置指定并选中[在聊天消息发送时触达]
   *
   * @param talkType        对话框类型[0好友、1群组]
   * @param talkElementPane 对话框元素面板
   * @param msgRemindLabel  消息提醒标签
   * @param idxFirst        是否设置首位
   * @param selected        是否选中
   * @param isRemind        是否提醒
   */
  fun updateTalkListIdxAndSelected(talkType: Int, talkElementPane: Pane, msgRemindLabel: Label,
                                   idxFirst: Boolean, selected: Boolean, isRemind: Boolean) {
    // 对话框ID、好友ID
    val talkBoxData = talkElementPane.userData as TalkBoxData
    // 填充到对话框
    val talkList = chatInit.get<ListView<Pane>>("talkList")
    // 对话空为空，初始化[置顶、选中、提醒]
    if (talkList.items.isEmpty()) {
      if (idxFirst) {
        talkList.items.add(0, talkElementPane)
      }
      if (selected) {
        // 设置对话框[√选中]
        talkList.selectionModel.select(talkElementPane)
      }
      isRemind(msgRemindLabel, talkType, isRemind)
      return
    }
    // 对话空不为空，判断第一个元素是否当前聊天Pane
    val firstPane = talkList.items[0]
    // 判断元素是否在首位，如果是首位可返回不需要重新设置首位
    if (talkBoxData.talkId == (firstPane.userData as TalkBoxData).talkId) {
      val selectedItem = talkList.selectionModel.selectedItem
      // 选中判断；如果第一个元素已经选中[说明正在会话]，那么清空消息提醒
      if (null == selectedItem) {
        isRemind(msgRemindLabel, talkType, isRemind)
        return
      }
      val selectedItemUserData: TalkBoxData = selectedItem.userData as TalkBoxData
      if (talkBoxData.talkId == selectedItemUserData.talkId) {
        clearRemind(msgRemindLabel)
      } else {
        isRemind(msgRemindLabel, talkType, isRemind)
      }
      return
    }
    if (idxFirst) {
      talkList.items.remove(talkElementPane)
      talkList.items.add(0, talkElementPane)
    }
    if (selected) {
      // 设置对话框[√选中]
      talkList.selectionModel.select(talkElementPane)
    }
    isRemind(msgRemindLabel, talkType, isRemind)
  }

  /**
   * 消息提醒
   *
   * @param msgRemindLabel 消息面板
   */
  private fun isRemind(msgRemindLabel: Label, talkType: Int, isRemind: Boolean) {
    if (!isRemind) return
    msgRemindLabel.isVisible = true
    // 群组直接展示小红点
    if (1 == talkType) {
      return
    }
    val remindCount = msgRemindLabel.userData as RemindCount
    // 超过10个展示省略号
    if (remindCount.count > 99) {
      msgRemindLabel.text = "···"
      return
    }
    val count: Int = remindCount.count + 1
    msgRemindLabel.userData = RemindCount(count)
    msgRemindLabel.text = count.toString()
  }

  private fun clearRemind(msgRemindLabel: Label) {
    msgRemindLabel.isVisible = false
    msgRemindLabel.userData = RemindCount(0)
  }

  /**
   * group_bar_chat：填充对话列表 & 对话框名称
   *
   * @param id   用户、群组等ID
   * @param name 用户、群组等名称
   * @param node 展现面板
   */
  fun setContentPaneBox(id: String, name: String, node: Node) {
    // 填充对话列表
    val contentPaneBox = chatInit.get<Pane>("content_pane_box")
    contentPaneBox.userData = id
    contentPaneBox.children.clear()
    contentPaneBox.children.add(node)
    // 对话框名称
    chatInit.get<Label>("content_name").text = name
  }
}