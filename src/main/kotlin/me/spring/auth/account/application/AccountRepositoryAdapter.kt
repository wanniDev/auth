package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import me.spring.auth.account.domain.AccountRepository
import me.spring.auth.account.infrastructure.jpa.JpaAccountRepository
import me.spring.auth.exception.NotFoundAccountException
import org.springframework.stereotype.Component

@Component
class AccountRepositoryAdapter(private val jpaAccountRepository: JpaAccountRepository) : AccountRepository {
    override fun save(account: Account): Account = jpaAccountRepository.save(account)

    override fun findById(id: Long): Account =
        jpaAccountRepository.findAccountById(id) ?: throw NotFoundAccountException()
    override fun findByUserId(userId: String): Account = jpaAccountRepository
        .findByUserId(userId) ?: throw NotFoundAccountException()

    override fun existByEmail(email: String): Boolean = jpaAccountRepository.existsByEmail(email)

    override fun existByUserId(userId: String): Boolean = jpaAccountRepository.existsByUserId(userId)
    override fun findByEmail(email: String): Account =
        jpaAccountRepository.findByEmail(email) ?: throw NotFoundAccountException()

    override fun existById(id: Long): Boolean = jpaAccountRepository.existsById(id)
}