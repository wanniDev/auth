package me.spring.auth.account.domain

interface AccountTokenRepository {
    fun save(accountToken: AccountToken): AccountToken
    fun findByAccountId(accountId: Long): AccountToken
    fun findTokenByAccountId(accountId: Long): String
    fun existByAccountId(id: Long): Boolean
}