package me.spring.auth.account.application.jwt

import me.spring.auth.account.application.AccountProcessor
import me.spring.auth.account.application.AccountRepositoryAdapter
import me.spring.auth.account.application.AuthHelper
import me.spring.auth.account.application.JoinHelper
import me.spring.auth.account.infrastructure.security.JwtAuthenticationToken
import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.AuthResponse
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.account.presentation.request.JoinResponse
import me.spring.auth.exception.ErrorMsg
import me.spring.auth.exception.NotFoundAccountException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Primary
@Qualifier("jwtAccountProcessor")
class JwtAccountProcessor(private val accountRepository: AccountRepositoryAdapter,
                          private val authHelper: AuthHelper,
                          @Qualifier("jwtJoinHelper") private val joinHelper: JoinHelper
): AccountProcessor {
    @Transactional
    override fun processJoin(joinRequest: JoinRequest): JoinResponse {
        joinHelper.checkDuplicated(accountRepository.existByEmail(joinRequest.email))

        val newAccount = joinHelper.produceNewAccount(joinRequest)
        val savedAccount = accountRepository.save(newAccount)

        return JoinResponse(savedAccount)
    }

    @Transactional
    override fun processAuth(authRequest: AuthRequest): AuthResponse {
        val account = accountRepository.findByEmail(authRequest.email)
        if (authHelper.passwdMatches(authRequest.password, account.password)) {
            val authToken = authHelper.authenticate(account) as JwtAuthenticationToken
            return authToken.details as AuthResponse
        }
        throw BadCredentialsException(ErrorMsg.AUTH_FAIL.msg)
    }

    @Transactional
    override fun invalidate(id: Long): Boolean {
        if (accountRepository.existById(id)) {
            return authHelper.invalidate(id)
        }
        throw NotFoundAccountException()
    }
}