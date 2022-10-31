package me.spring.auth.account.infrastructure.jpa

import me.spring.auth.account.domain.Account
import org.springframework.data.jpa.repository.JpaRepository

interface JpaAccountRepository : JpaRepository<Account?, Long?>