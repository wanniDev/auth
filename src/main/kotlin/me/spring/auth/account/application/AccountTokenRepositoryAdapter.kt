package me.spring.auth.account.application

import me.spring.auth.account.domain.AccountToken
import me.spring.auth.account.domain.AccountTokenRepository
import me.spring.auth.account.infrastructure.jpa.JpaAccountTokenRepository
import me.spring.auth.exception.NotFountAccountTokenException
import org.springframework.stereotype.Component

@Component
class AccountTokenRepositoryAdapter(private val jpaAccountTokenRepository: JpaAccountTokenRepository): AccountTokenRepository {
    override fun save(accountToken: AccountToken): AccountToken {
        return jpaAccountTokenRepository.save(accountToken)
    }

    override fun findByAccountId(accountId: Long): AccountToken {
        return jpaAccountTokenRepository.findByAccountId(accountId) ?: throw NotFountAccountTokenException()
    }

    override fun findTokenByAccountId(accountId: Long): String {
        return jpaAccountTokenRepository.findTokenByAccountId(accountId) ?: throw NotFountAccountTokenException()
    }

    override fun existByAccountId(accountId: Long): Boolean {
        return jpaAccountTokenRepository.existsByAccountId(accountId)
    }
}