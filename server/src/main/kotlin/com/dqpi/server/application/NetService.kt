package com.dqpi.server.application

import com.dqpi.server.domain.net.modal.ChannelUserInfo
import com.dqpi.server.domain.net.modal.ChannelUserReq
import com.dqpi.server.domain.net.modal.NetServerInfo
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface NetService {
  fun queryNettyServerInfo(): Mono<NetServerInfo>
  fun queryChannelUserCount(req: ChannelUserReq): Mono<Long>
  fun queryChannelUserList(req: ChannelUserReq): Flux<ChannelUserInfo>
}