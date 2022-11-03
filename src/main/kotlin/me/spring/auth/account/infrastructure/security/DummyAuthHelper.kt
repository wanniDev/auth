package me.spring.auth.account.infrastructure.security

import me.spring.auth.account.application.AuthHelper
import me.spring.auth.account.application.AuthToken
import me.spring.auth.account.domain.Account
import org.springframework.stereotype.Component

@Component
class DummyAuthHelper : AuthHelper {
    override fun authenticate(account: Account): AuthToken {
        return DummyAuthToken(account)
    }

    override fun passwdMatches(passwd1: String?, passwd2: String): Boolean {
        return passwd1.equals(passwd2)
    }
}