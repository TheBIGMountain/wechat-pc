package utils

import com.dyuproject.protostuff.LinkedBuffer
import com.dyuproject.protostuff.ProtostuffIOUtil.mergeFrom
import com.dyuproject.protostuff.ProtostuffIOUtil.toByteArray
import com.dyuproject.protostuff.Schema
import com.dyuproject.protostuff.runtime.RuntimeSchema.createFrom
import org.springframework.objenesis.ObjenesisStd
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

val objenesis = ObjenesisStd()

inline fun <reified T> T.serialize(): Mono<ByteArray> {
  @Suppress("UNCHECKED_CAST")
  val schema = createFrom(this!!::class.java) as Schema<T>
  val buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE)
  return schema.toMono()
    .map { toByteArray(this, it, buffer) }
    .doFinally { buffer.clear() }
}

fun <T> ByteArray.deserialize(clazz: Class<T>): T {
  val obj = objenesis.newInstance(clazz)
  @Suppress("UNCHECKED_CAST")
  val schema = createFrom(obj!!::class.java) as Schema<T>
  mergeFrom(this, obj, schema)
  return obj
}
