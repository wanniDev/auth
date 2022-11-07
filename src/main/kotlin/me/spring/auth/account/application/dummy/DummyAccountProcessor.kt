package me.spring.auth.account.application.dummy

import me.spring.auth.account.application.AccountProcessor
import me.spring.auth.account.application.AccountRepositoryAdapter
import me.spring.auth.account.application.AuthHelper
import me.spring.auth.account.application.JoinHelper
import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.AuthResponse
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.account.presentation.request.JoinResponse
import me.spring.auth.exception.AccountAuthException
import me.spring.auth.exception.DuplicateAccountException


class DummyAccountProcessor(private val accountRepository: AccountRepositoryAdapter,
                            private val authHelper: AuthHelper, private val joinHelper: JoinHelper
): AccountProcessor {

    override fun processJoin(joinRequest: JoinRequest): JoinResponse {
        val email = joinRequest.email

        if (accountRepository.existByEmail(email)) {
                throw DuplicateAccountException("Email [$email] already exisits..")
        }
        val newAccount = joinHelper.produceNewAccount(joinRequest)
        return JoinResponse(accountRepository.save(newAccount))
    }

    override fun processAuth(authRequest: AuthRequest): AuthResponse {
        val account = accountRepository.findByUserId(authRequest.email)
        if (authHelper.passwdMatches(account.password, authRequest.password)) {
            val authToken = authHelper.authenticate(account)
            return AuthResponse(authToken)
        }
        throw AccountAuthException()
    }
}