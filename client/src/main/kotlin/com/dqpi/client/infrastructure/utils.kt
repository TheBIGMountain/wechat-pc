package com.dqpi.client.infrastructure

import java.util.concurrent.ConcurrentHashMap

const val channelCache = "channel"
const val userIdCache = "userId"

val cacheMap = ConcurrentHashMap<String, Any>()

fun String.addCache(obj: Any) { cacheMap[this] = obj }

inline fun <reified T> String.getCache(): T = T::class.java.cast(cacheMap[this])
