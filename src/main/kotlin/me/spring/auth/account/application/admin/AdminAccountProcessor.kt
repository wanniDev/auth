package me.spring.auth.account.application.admin

import me.spring.auth.account.application.AccountProcessor
import me.spring.auth.account.application.AccountRepositoryAdapter
import me.spring.auth.account.application.AuthHelper
import me.spring.auth.account.application.JoinHelper
import me.spring.auth.account.infrastructure.security.Role
import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.AuthResponse
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.account.presentation.request.JoinResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("adminAccountProcessor")
class AdminAccountProcessor(private val accountRepository: AccountRepositoryAdapter,
                            private val authHelper: AuthHelper, @Qualifier("adminJoinHelper") private val joinHelper: JoinHelper): AccountProcessor {
    override fun processJoin(joinRequest: JoinRequest): JoinResponse {
        joinHelper.checkDuplicated(accountRepository.existByEmail(joinRequest.email))

        val newAccount = joinHelper.produceNewAccount(joinRequest)
        val savedAccount = accountRepository.save(newAccount)

        return JoinResponse(savedAccount)
    }

    override fun processAuth(authRequest: AuthRequest): AuthResponse {
        TODO("Not yet implemented")
    }
}