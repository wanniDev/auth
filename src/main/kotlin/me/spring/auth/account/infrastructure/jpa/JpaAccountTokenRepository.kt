package me.spring.auth.account.infrastructure.jpa

import me.spring.auth.account.domain.AccountToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface JpaAccountTokenRepository: JpaRepository<AccountToken, Long> {
    fun findByAccountId(accountId: Long): AccountToken?

    fun existsByAccountId(accountId: Long): Boolean

    @Query("select a.token from AccountToken a where a.account.id = ?1")
    fun findTokenByAccountId(accountId: Long): String?
}