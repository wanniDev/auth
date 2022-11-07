package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import me.spring.auth.account.infrastructure.jwt.JWT
import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.AuthResponse
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.exception.AccountAuthException
import me.spring.auth.exception.DuplicateAccountException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

// TODO AccountProcessor 추상화
@Component
class AccountProcessor(private val accountRepository: AccountRepositoryAdapter,
                       private val authHelper: AuthHelper) {
    @Value("\${jwt.token.issuer}")
    private lateinit var issuer :String

    @Value("\${jwt.token.clientSecret}")
    private lateinit var clientSecret: String

    @Value("\${jwt.token.expirySeconds}")
    private var expirySeconds: Int ?= 0

    @Transactional
    fun processJoin(joinRequest: JoinRequest): Account {
        val userId = joinRequest.userId
        val email = joinRequest.email
        if (isDuplicated(userId, email))
            throw DuplicateAccountException("UserId [$userId], email [$email] already exisits..")

        val account = Account(joinRequest.userId, joinRequest.credential, joinRequest.name, joinRequest.email, joinRequest.phone)
        val result = accountRepository.save(account)
        // TODO jwt 추상화
        val jwt = JWT(issuer, clientSecret, expirySeconds)
        val newToken = jwt.newToken(JWT.Claims.of(result.id, result.name, result.email, arrayOf("ROLE_USER")))

        return result
    }

    private fun isDuplicated(userId: String, email: String): Boolean =
        accountRepository.existByUserId(userId) && accountRepository.existByEmail(email)

    @Transactional
    fun processAuth(authRequest: AuthRequest): AuthResponse {
        val account = accountRepository.findByUserId(authRequest.userId)
        if (authHelper.passwdMatches(account.password, authRequest.password)) {
            val authToken = authHelper.authenticate(account)
            return AuthResponse(authToken)
        }
        throw AccountAuthException()
    }
}