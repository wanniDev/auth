package me.spring.auth.account.infrastructure.jpa

import me.spring.auth.account.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface JpaAccountRepository : JpaRepository<Account?, Long?> {

    fun findByEmail(email: String): Account?

    fun findAccountById(id: Long): Account?
    fun findByUserId(userId: String): Account?
    fun existsByEmail(email: String): Boolean
    fun existsByUserId(userId: String): Boolean
}