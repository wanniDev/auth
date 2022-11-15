package me.spring.auth.account.infrastructure.jpa

import me.spring.auth.account.domain.AccountToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface JpaAccountTokenRepository: JpaRepository<AccountToken, Long> {
    fun findAccountTokenById(id: Long): AccountToken?
    fun findByAccountId(accountId: Long): AccountToken?

    fun existsByAccountId(accountId: Long): Boolean
    @Query("select at.token from AccountToken at where at.id = ?1")
    fun findTokenById(id: Long): String?
    @Query("select at.token from AccountToken at where at.account.id = ?1")
    fun findTokenByAccountId(accountId: Long): String?
}