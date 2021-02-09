package com.dqpi.server.infrastructure.repository

import com.dqpi.server.domain.net.modal.ChannelUserInfo
import com.dqpi.server.domain.net.modal.ChannelUserReq
import com.dqpi.server.domain.net.repository.NetRepository
import com.dqpi.server.infrastructure.common.toChannelUserInfo
import com.dqpi.server.infrastructure.dao.UserDao
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class NetRepositoryImpl(
  private val userDao: UserDao
): NetRepository {
  override fun queryChannelUserCount(req: ChannelUserReq): Mono<Long> {
    return userDao.countByUserIdAndUserNickNameLike(req.userId, req.userNickName)
  }

  override fun queryChannelUserList(req: ChannelUserReq): Flux<ChannelUserInfo> {
    return userDao.findAllByUserIdAndUserNickNameLike(req.userId, req.userNickName)
      .toChannelUserInfo()
  }
}