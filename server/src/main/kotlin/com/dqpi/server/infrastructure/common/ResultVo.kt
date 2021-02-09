package com.dqpi.server.infrastructure.common

class ResultVo<T>(
  val code: Int,
  val msg: String,
  val count: Long? = null,
  val data: T? = null
)

fun String.toResultVo() = ResultVo<Unit>(1, this)

fun Exception.toResultVo() = ResultVo<Unit>(1, message!!)

fun <T> T.toResultVo() = ResultVo(0, "", 1, this)

fun <T> T.toResultVo(count: Long) = ResultVo(0, "", count, this)

