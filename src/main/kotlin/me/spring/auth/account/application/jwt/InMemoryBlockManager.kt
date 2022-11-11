package me.spring.auth.account.application.jwt

import me.spring.auth.account.application.AuthBlockManager
import me.spring.auth.account.domain.Id
import me.spring.auth.account.infrastructure.security.AuthInfo
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class InMemoryBlockManager: AuthBlockManager<Long> {
    private val blockList: MutableSet<Id<AuthInfo, Long>> = HashSet()

    override fun existsById(id: Long): Boolean {
        return blockList.contains(Id.of(AuthInfo::class.java, id)!!)
    }

    override fun add(id: Long): Boolean {
        return blockList.add(Id.of(AuthInfo::class.java, id)!!)
    }
}