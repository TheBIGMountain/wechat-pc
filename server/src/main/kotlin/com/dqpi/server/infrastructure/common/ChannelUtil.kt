package com.dqpi.server.infrastructure.common

import io.netty.channel.Channel
import io.netty.channel.group.ChannelGroup
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.GlobalEventExecutor
import java.util.concurrent.ConcurrentHashMap

private val userChannel = ConcurrentHashMap<String, Channel>()
private val userChannelId = ConcurrentHashMap<String, String>()
private val groupChannel = ConcurrentHashMap<String, ChannelGroup>()

fun String.addChannel(channel: Channel) {
  userChannel[this] = channel
  userChannelId["${channel.id()}"] = this
}

fun String.removeChannel() = userChannelId[this]?.let { userChannel.remove(it) }

fun String.removeUserChannel() = userChannel.remove(this)

fun String.toChannel() = userChannel[this]

fun String.addChannelGroup(channel: Channel) {
  val groupChannel = groupChannel.getOrPut(this) { DefaultChannelGroup(GlobalEventExecutor.INSTANCE) }
  synchronized(this) { groupChannel.add(channel) }
}

fun Channel.removeChannelGroup() = groupChannel.values.forEach { it.remove(this) }

fun String.toChannelGroup() = groupChannel[this]



