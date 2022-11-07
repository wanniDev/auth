package me.spring.auth.account.application

import me.spring.auth.account.infrastructure.security.JwtAuthenticationToken
import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.AuthResponse
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.account.presentation.request.JoinResponse
import me.spring.auth.exception.AccountAuthException
import me.spring.auth.exception.AccountExceptionMsg
import me.spring.auth.exception.DuplicateAccountException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class JWTAccountProcessor(private val accountRepository: AccountRepositoryAdapter,
                          private val authHelper: AuthHelper, private val joinHelper: JoinHelper): AccountProcessor {
    @Transactional
    override fun processJoin(joinRequest: JoinRequest): JoinResponse {
        val email = joinRequest.email
        if (accountRepository.existByEmail(email)) {
            throw DuplicateAccountException()
        }
        val newAccount = joinHelper.produceNewAccount(joinRequest)
        val savedAccount = accountRepository.save(newAccount)
        val token = joinHelper.produceNewToken(savedAccount)

        return JoinResponse(token, savedAccount)
    }

    @Transactional
    override fun processAuth(authRequest: AuthRequest): AuthResponse {
        val account = accountRepository.findByEmail(authRequest.email)
        if (authHelper.passwdMatches(authRequest.password, account.password)) {
            val authToken = authHelper.authenticate(account) as JwtAuthenticationToken
            return AuthResponse(authToken.details)
        }
        throw BadCredentialsException(AccountExceptionMsg.AUTH_FAIL.msg)
    }
}