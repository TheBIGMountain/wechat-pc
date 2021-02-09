package com.dqpi.server.domain.net.repository

import com.dqpi.server.domain.net.modal.ChannelUserInfo
import com.dqpi.server.domain.net.modal.ChannelUserReq
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface NetRepository {
  fun queryChannelUserCount(req: ChannelUserReq): Mono<Long>
  fun queryChannelUserList(req: ChannelUserReq): Flux<ChannelUserInfo>
}