package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import me.spring.auth.account.infrastructure.jwt.JWT
import me.spring.auth.account.presentation.request.JoinRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class JWTJoinHelper(private val passwordEncoder: PasswordEncoder, private val jwt: JWT): JoinHelper {
    override fun produceNewAccount(joinRequest: JoinRequest): Account {
        val produceNewAccount = super.produceNewAccount(joinRequest)
        produceNewAccount.encodePasswd(passwordEncoder)
        return produceNewAccount
    }

    override fun produceNewToken(account: Account): String {
        return jwt.newToken(JWT.Claims.of(account.id, account.name, account.email, arrayOf("ROLE_USER")))
    }
}