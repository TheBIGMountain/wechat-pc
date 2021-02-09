package com.dqpi.client.utils

import com.dqpi.client.view.chat.element.group_bar_chat.ElementTalk
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap


val talkMap = ConcurrentHashMap<String, ElementTalk>(16)

fun String.createTalkPaneId() = "ElementTalkId_createTalkPaneId_$this"
fun String.analysisTalkPaneId() = split("_")[2]
fun String.createInfoBoxListId() = "ElementTalkId_createInfoBoxListId_$this"
fun String.analysisInfoBoxListId() = split("_")[2]
fun String.createMsgDataId() = "ElementTalkId_createMsgDataId_$this"
fun String.analysisMsgDataId() = split("_")[2]
fun String.createMsgSketchId() = "ElementTalkId_createMsgSketchId_$this"
fun String.analysisMsgSketchId() = split("_")[2]
fun String.createFriendGroupId() = "ElementTalkId_createFriendGroupId_$this"

fun LocalDateTime.simpleDate(): String {
  return if (isToday(this))
    DateTimeFormatter.ofPattern("HH:mm").format(this)
  else
    DateTimeFormatter.ofPattern("yy/MM/dd").format(this)
}

fun String.getWidth(): Double {
  var width = 0.0
  forEach { _ -> width += 16 }
  width += 22
  return if (width > 450) 450.0 else { if (width < 50) 50.0 else width }
}

fun String.getHeight(): Double {
  var width = 0
  forEach { _ -> width += 16 }
  width += 22

  val remainder = (width % 450).toDouble()
  var line = (width / 450)

  if (remainder != 0.0)  line += 1

  val autoHeight = (line * 24 + 10).toDouble()

  return if (autoHeight < 30) 30.0 else autoHeight
}

private fun isToday(date: LocalDateTime): Boolean {
  val year1 = date.year
  val month1 = date.monthValue
  val day1 = date.dayOfMonth

  val now = LocalDateTime.now();
  val year2 = now.year
  val month2 = now.monthValue
  val day2 = now.dayOfMonth
  return year1 == year2 && month1 == month2 && day1 == day2;
}

