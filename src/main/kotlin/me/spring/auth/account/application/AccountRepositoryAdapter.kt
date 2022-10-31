package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import me.spring.auth.account.domain.AccountRepository
import me.spring.auth.account.infrastructure.jpa.JpaAccountRepository
import java.util.*

class AccountRepositoryAdapter(private val jpaAccountRepository: JpaAccountRepository) : AccountRepository {
    override fun save(account: Account): Account = jpaAccountRepository.save(account)
    override fun findById(id: Long): Optional<Account?> = jpaAccountRepository.findById(id)
}