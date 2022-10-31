package me.spring.auth.account.domain

import org.springframework.data.repository.Repository
import java.util.Optional

interface AccountRepository {
    fun save(account: Account): Account
    fun findById(id: Long): Optional<Account?>

    fun existByEmail(email: String): Boolean

    fun existByUserId(userId: String): Boolean
}