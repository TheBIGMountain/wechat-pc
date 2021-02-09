package com.dqpi.client.view.chat

import com.dqpi.client.utils.createFriendGroupId
import com.dqpi.client.utils.createTalkPaneId
import com.dqpi.client.utils.talkMap
import com.dqpi.client.view.chat.data.GroupsData
import com.dqpi.client.view.chat.data.RemindCount
import com.dqpi.client.view.chat.data.TalkData
import com.dqpi.client.view.chat.element.group_bar_chat.*
import com.dqpi.client.view.chat.element.group_bar_friend.toElementFriendLuckUser
import com.dqpi.client.view.chat.element.group_bar_friend.toElementFriendUser
import com.dqpi.client.view.chat.element.group_bar_friend.toFriendGroup
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.layout.Pane
import reactor.kotlin.core.publisher.toMono
import java.time.LocalDateTime
import java.util.*

class ChatController(private val chatEvent: ChatEvent): ChatInit(chatEvent), ChatMethod {
  private val chatView = ChatView(this, chatEvent)
  private val defaultChatEvent = DefaultChatEvent(this, chatEvent, this)


  /**
   * 打开窗口
   */
  override fun doShow() {
    show()
  }

  /**
   * 设置登陆用户头像
   *
   * @param userId       用户ID
   * @param userNickName 用户昵称
   * @param userHead     头像图片名称
   */
  override fun setUserInfo(userId: String, userNickName: String, userHead: String) {
    super.userId = userId
    super.userNickName = userNickName
    super.userHead = userHead
    get<Button>("bar_portrait").style = "-fx-background-image: url('/fxml/chat/img/head/${userHead}.png')"
  }

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
  override fun addTalkBox(
    talkIdx: Int, talkType: Int, talkId: String, talkName: String,
    talkHead: String, talkSketch: String, talkDate: LocalDateTime, selected: Boolean,
  ) {
    val talkList = get<ListView<Pane>>("talkList")
    // 判断会话框是否有该对象
    talkMap[talkId]?.let {
      talkList.lookup("#${talkId.createTalkPaneId()}") ?:
        talkList.items.add(talkIdx, it.pane).let { _ -> fillInfoBox(it, talkName) }

      // 设置选中
      if (selected) talkList.selectionModel.select(it.pane)

      // 填充对话框消息栏
      fillInfoBox(it, talkName)
      return
    }

    // 初始化对话框元素
    talkId.getElementTalk(talkType, talkName, talkHead, talkSketch, talkDate).also {
      talkMap[talkId] = it
      talkList.items.let { items ->
        if (talkIdx >= 0) items.add(talkIdx, it.pane)
        else items.add(it.pane)

        if (selected) talkList.selectionModel.select(it.pane)

        // 对话框元素点击事件
        it.pane.setOnMousePressed { _ ->
          // 填充消息栏
          fillInfoBox(it, talkName)
          // 清除消息提醒
          it.msgRemind.userData = RemindCount()
          it.msgRemind.isVisible = false
        }

        // 鼠标事件[移入/移出]
        it.pane.setOnMouseEntered { _ ->
          it.delete.isVisible = true
        }
        it.pane.setOnMouseExited { _ ->
          it.delete.isVisible = false
        }
        // 填充消息栏
        fillInfoBox(it, talkName)

        // 从对话框中删除
        it.delete.setOnMouseClicked { _ ->
          items.remove(it.pane)
          get<Pane>("info_pane_box").children.clear()
          get<Pane>("info_pane_box").userData = null
          get<Label>("info_name").text = ""
          it.infoBoxList.items.clear()
          it.msgSketch.text = ""
          chatEvent.doEventDelTalkUser(super.userId, talkId)
        }
      }
    }
  }

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
  override fun addTalkMsgUserLeft(
    talkId: String, msg: String, msgType: Int, msgData: LocalDateTime,
    idxFirst: Boolean, selected: Boolean, isRemind: Boolean,
  ) {
    val talkElement = talkMap[talkId]!!
    talkElement.infoBoxList.toMono()
      .map { it.userData }
      .cast(TalkData::class.java)
      .map { it.talkName.left(it.talkHead, msg, msgType).pane }
      // 消息填充
      .doOnNext { talkElement.infoBoxList.items.add(it) }
      // 滚动条
      .doOnNext { talkElement.infoBoxList.scrollTo(it) }
      .doOnNext { talkMap[talkId]!!.fillMsgSketch(if (msgType == 0) msg else "[表情]", msgData) }
      // 设置位置&选中
      .doOnNext { chatView.updateTalkListIdxAndSelected(0, talkElement.pane, talkElement.msgRemind, idxFirst, selected, isRemind) }
      .subscribe()
  }

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
  override fun addTalkMsgGroupLeft(talkId: String, userId: String, userNickName: String,
                                   userHead: String, msg: String, msgType: Int,
                                   msgDate: LocalDateTime, idxFirst: Boolean, selected: Boolean,
                                   isRemind: Boolean) {
    // 自己的消息抛弃
    if (super.userId == userId) return
    val talkElement = talkMap[talkId]
    if (talkElement == null) {
      val groupsData = get<Pane>(talkId.createFriendGroupId()).userData as GroupsData? ?: return
      addTalkBox(0, 1, talkId, groupsData.groupName, groupsData.groupHead, "${userNickName}:${msg}", msgDate, false)
      // 事件通知(开启与群组发送消息)
      chatEvent.doEventAddTalkGroup(super.userId, talkId)
    }
    talkElement?.infoBoxList?.let {
      val left = userNickName.left(userHead, msg, msgType).pane
      // 消息填充
      it.items.add(left)
      // 滚动条
      it.scrollTo(left)
      talkElement.fillMsgSketch(if (msgType == 0) "${userNickName}: $msg" else ": [表情]", msgDate)
      // 设置位置&选中
      chatView.updateTalkListIdxAndSelected(1, talkElement.pane, talkElement.msgRemind, idxFirst, selected, isRemind)
    }
  }

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
  override fun addTalkMsgRight(
    talkId: String, msg: String, msgType: Int, msgDate: LocalDateTime,
    idxFirst: Boolean, selected: Boolean, isRemind: Boolean,
  ) {
    val talkElement = talkMap[talkId]!!
    val listView = talkElement.infoBoxList
    val right = userNickName.right(userHead, msg, msgType).pane

    // 消息填充
    listView.items.add(right)
    // 滚动条
    listView.scrollTo(right)
    talkElement.fillMsgSketch(if (msgType == 0) "${userNickName}: $msg" else ": [表情]", msgDate)
    // 设置位置&选中
    chatView.updateTalkListIdxAndSelected(0, talkElement.pane, talkElement.msgRemind, idxFirst, selected, isRemind)
  }

  /**
   * 好友列表添加‘群组’
   *
   * @param groupId   群组ID
   * @param groupName 群组名称
   * @param groupHead 群组头像
   */
  override fun addFriendGroup(groupId: String, groupName: String, groupHead: String) {
    val elementFriendGroup = groupId.toFriendGroup(groupName, groupHead)
    val pane = elementFriendGroup.groupPane

    // 添加到群组列表
    val groupListView = get<ListView<Pane>>("groupListView")
    val items = groupListView.items
    items.add(pane)
    groupListView.prefHeight = (80 * items.size).toDouble()
    get<Pane>("friendGroupList").prefHeight = (80 * items.size).toDouble()

    // 群组，内容框[初始化，未装载]，承载群组信息内容，点击按钮时候填充
    Pane().apply {
      setPrefSize(850.0, 560.0)
      styleClass.add("friendGroupDetailContent")

      children.add(Button().apply {
        id = groupId
        styleClass.add("friendGroupSendMsgButton")
        setPrefSize(176.0, 50.0)
        layoutX = 337.0
        layoutY = 450.0
        text = "发送消息"
        defaultChatEvent.doEventOpenFriendGroupSendMsg(this, groupId, groupName, groupHead)
      })

      // 添加监听事件
      pane.setOnMousePressed {
        clearViewListSelectedAll(get("friendList"), get("userListView"))
        chatView.setContentPaneBox(groupId, groupName, this)
      }
      chatView.setContentPaneBox(groupId, groupName, this)
    }
  }

  /**
   * 好友列表添加‘用户’
   *
   * @param selected     选中;true/false
   * @param userId       好友ID
   * @param userNickName 好友昵称
   * @param userHead     好友头像
   */
  override fun addFriendUser(selected: Boolean, userId: String, userNickName: String, userHead: String) {
    val friendUser = userId.toElementFriendUser(userNickName, userHead)
    val pane = friendUser.pane

    // 添加到好友列表
    val userListView = get<ListView<Pane>>("userListView")
    val items = userListView.items
    items.add(pane)
    userListView.prefHeight = (80 * items.size).toDouble()
    get<Pane>("friendUserList").prefHeight = (80 * items.size).toDouble()

    // 选中
    if (selected) {
      userListView.selectionModel.select(pane)
    }

    // 好友，内容框[初始化，未装载]，承载好友信息内容，点击按钮时候填充
    Pane().apply {
      setPrefSize(850.0, 560.0)
      styleClass.add("friendUserDetailContent")

      children.add(Button().apply {
        id = userId
        styleClass.add("friendUserSendMsgButton")
        setPrefSize(176.0, 50.0)
        layoutX = 337.0
        layoutY = 450.0
        text = "发送消息"
        defaultChatEvent.doEventOpenFriendUserSendMsg(this, userId, userNickName, userHead)
      })

      // 添加监听事件
      pane.setOnMousePressed {
        clearViewListSelectedAll(get("friendList"), get("groupListView"))
        chatView.setContentPaneBox(userId, userNickName, this)
      }
      chatView.setContentPaneBox(userId, userNickName, this)
    }
  }

  /**
   * 缘分好友 | 默认添加10个好友
   *
   * @param userId       好友ID
   * @param userNickName 好友昵称
   * @param userHead     好友头像
   * @param status       状态；0添加、1允许、2已添加
   */
  override fun addLuckFriend(userId: String, userNickName: String, userHead: String, status: Int) {
    val friendLuckUser = userId.toElementFriendLuckUser(userNickName, userHead, status)
    val pane = friendLuckUser.pane
    // 添加到好友列表
    val friendLuckListView = get<ListView<Pane>>("friendLuckListView")
    val items = friendLuckListView.items
    items.add(pane)
    // 点击事件
    friendLuckUser.status.setOnMousePressed {
      chatEvent.doEventAddLuckUser(super.userId, userId)
    }
  }


  /**
   * 私有方法
   * 填充对话框消息内容
   *
   * @param talkElement 对话框元素
   * @param talkName    对话框名称
   */
  private fun fillInfoBox(talkElement: ElementTalk, talkName: String) {
    val talkId = talkElement.pane.userData.toString()
    // 填充对话列表
    val infoPaneBox = get<Pane>("info_pane_box")
    // 判断是否已经填充[talkId]，当前已填充则返回
    infoPaneBox.userData?.let {
      if (talkId == it.toString()) return
    }
    val listView: ListView<Pane> = talkElement.infoBoxList
    infoPaneBox.userData = talkId
    infoPaneBox.children.clear()
    infoPaneBox.children.add(listView)
    // 对话框名称
    get<Label>("info_name").text = talkName
  }

  override fun getToolFaceX(): Double {
    return x + width - 960
  }

  override fun getToolFaceY(): Double {
    return y + height - 180
  }
}