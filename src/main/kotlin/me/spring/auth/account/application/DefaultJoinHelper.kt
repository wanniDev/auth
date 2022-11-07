package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import me.spring.auth.account.presentation.request.JoinRequest
import org.springframework.stereotype.Component

@Component
class DefaultJoinHelper(protected val accountRepository: AccountRepositoryAdapter): JoinHelper