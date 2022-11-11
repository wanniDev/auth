package me.spring.auth.account.domain

interface AccountRepository {
    fun save(account: Account): Account
    fun findById(id: Long): Account
    fun findByUserId(userId: String): Account
    fun existByEmail(email: String): Boolean
    fun existByUserId(userId: String): Boolean
    fun findByEmail(email: String): Account
    fun existById(id: Long): Boolean
}