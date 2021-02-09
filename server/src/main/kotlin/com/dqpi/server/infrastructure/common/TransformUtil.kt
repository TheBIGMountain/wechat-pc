package com.dqpi.server.infrastructure.common

import com.dqpi.server.domain.net.modal.ChannelUserInfo
import com.dqpi.server.domain.user.modal.*
import com.dqpi.server.infrastructure.po.ChatRecord
import com.dqpi.server.infrastructure.po.Groups
import com.dqpi.server.infrastructure.po.TalkBox
import com.dqpi.server.infrastructure.po.User
import protocol.friend.dto.UserDTO
import protocol.login.dto.ChatRecordDTO
import protocol.login.dto.ChatTalkDTO
import protocol.login.dto.GroupsDTO
import protocol.login.dto.UserFriendDTO
import protocol.msg.MsgGroupReq
import protocol.msg.MsgGroupRes
import protocol.msg.MsgReq
import protocol.talk.TalkNoticeRes
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import java.time.LocalDateTime

fun Mono<User>.toUserInfo() = map { UserInfo(it.userId, it.userNickName, it.userHead) }

fun Mono<User>.toUserFriendInfo() = map { UserFriendInfo(it.userId, it.userNickName, it.userHead) }

fun Mono<Groups>.toGroupsInfo() = map { GroupsInfo(it.groupId, it.groupName, it.groupHead) }

fun Mono<UserInfo>.toTalkNoticeRes() = map { TalkNoticeRes(it.userId, it.userNickName, it.userHead, "", LocalDateTime.now()) }

fun Flux<User>.toLuckUserInfo() = map { LuckUserInfo(it.userId, it.userNickName, it.userHead, 0) }

fun Flux<ChatRecord>.toChatRecordInfo() = map { ChatRecordInfo(it.userId, it.friendId, it.msgContent, it.msgType, it.msgDate, it.talkType) }

fun Flux<User>.toChannelUserInfo() = map { ChannelUserInfo(it.userId, it.userNickName, it.userHead, false) }

fun TalkBoxInfo.toChatTalkDTO() = ChatTalkDTO(talkId, talkType, talkName, talkHead, talkSketch, talkDate)

fun Flux<GroupsInfo>.toGroupsDto() = map { GroupsDTO(it.groupId, it.groupName, it.groupHead) }

fun Flux<UserFriendInfo>.toUserFriendDTO() = map { UserFriendDTO(it.friendId, it.friendName, it.friendHead) }

fun Flux<LuckUserInfo>.toUserDTO() = map { UserDTO(it.userId, it.userNickName, it.userHead, it.status) }

fun MsgGroupReq.toChatRecordInfo() = ChatRecordInfo(userId, talkId, msgText, msgType, msgDate, Group)

fun MsgReq.toChatRecordInfo() = ChatRecordInfo(userId, friendId, msgText, msgType, msgDate, Friend)

fun Mono<UserInfo>.toMsgGroupRes(msg: MsgGroupReq) = map {
  MsgGroupRes(
    userId = msg.userId,
    talkId = msg.talkId,
    userNickName = it.userNickName,
    userHead = it.userHead,
    msg = msg.msgText,
    msgType = msg.msgType,
    msgDate = msg.msgDate
  )
}

fun Flux<ChatRecordInfo>.toChatRecordDTO(talkId: String, userId: String) = map {
  ChatRecordDTO(
    talkId = talkId,
    userId = if (userId == it.userId) it.userId else it.friendId,
    msgUserType = if (userId == it.userId) 0 else 1,
    msgContent = it.msgContent,
    msgType = it.msgType,
    msgDate = it.msgDate
  )
}

@JvmName("toChatRecordDTOChatRecordInfoUserInfo")
fun Flux<Tuple2<ChatRecordInfo, UserInfo>>.toChatRecordDTO(talkId: String, userId: String) = map {
  ChatRecordDTO(
    talkId = talkId,
    userId = it.t2.userId,
    userNickName = it.t2.userNickName,
    userHead = it.t2.userHead,
    msgContent = it.t1.msgContent,
    msgDate = it.t1.msgDate,
    msgUserType = if (userId == it.t1.userId) 0 else 1,
    msgType = it.t1.msgType
  )
}

fun ChatRecordInfo.toChatRecord() = ChatRecord(
  userId = userId,
  friendId = friendId,
  msgContent = msgContent,
  msgType = msgType,
  msgDate = msgDate,
  talkType = talkType,
  createTime = LocalDateTime.now(),
  updateTime = LocalDateTime.now()
)

fun String.toTalkBox(talkId: String, talkType: Int, createTime: LocalDateTime = LocalDateTime.now(), updateTime: LocalDateTime = LocalDateTime.now())
= TalkBox(
  userId = this,
  talkId = talkId,
  talkType = talkType,
  createTime = createTime,
  updateTime = updateTime
)

fun Mono<User>.toTalkBoxInfo(talkBox: TalkBox) = map {
  TalkBoxInfo(Friend, it.userId, it.userNickName, it.userHead, "", talkBox.updateTime)
}

@JvmName("toTalkBoxInfoGroups")
fun Mono<Groups>.toTalkBoxInfo(talkBox: TalkBox) = map {
  TalkBoxInfo(Group, it.groupId, it.groupName, it.groupHead, "", talkBox.updateTime)
}
