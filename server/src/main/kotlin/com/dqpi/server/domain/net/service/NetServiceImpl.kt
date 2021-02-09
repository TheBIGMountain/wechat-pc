package com.dqpi.server.domain.net.service

import com.dqpi.server.application.NetService
import com.dqpi.server.domain.net.modal.ChannelUserInfo
import com.dqpi.server.domain.net.modal.ChannelUserReq
import com.dqpi.server.domain.net.modal.NetServerInfo
import com.dqpi.server.domain.net.repository.NetRepository
import com.dqpi.server.infrastructure.common.toChannel
import com.dqpi.server.infrastructure.config.NettyConfig
import com.dqpi.server.socket.NettyServer
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class NetServiceImpl(
  private val netRepository: NetRepository,
  private val nettyConfig: NettyConfig,
  private val nettyServer: NettyServer
): NetService {
  override fun queryNettyServerInfo(): Mono<NetServerInfo> {
    return NetServerInfo(nettyConfig.ip, nettyConfig.port, nettyServer.channel.isActive).toMono()
  }

  override fun queryChannelUserCount(req: ChannelUserReq): Mono<Long> {
    return netRepository.queryChannelUserCount(req)
  }

  override fun queryChannelUserList(req: ChannelUserReq): Flux<ChannelUserInfo> {
    return netRepository.queryChannelUserList(req)
      .doOnNext { c -> c.userId.toChannel().let { c.status = !(it == null || !it.isActive) } }
  }

}