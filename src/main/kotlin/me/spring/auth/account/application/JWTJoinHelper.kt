package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import me.spring.auth.account.infrastructure.jwt.JWT
import org.springframework.stereotype.Component

@Component
class JWTJoinHelper(private val jwt: JWT): JoinHelper {

    override fun produceNewToken(account: Account): String {
        return jwt.newToken(JWT.Claims.of(account.id, account.name, account.email, arrayOf("ROLE_USER")))
    }
}