package me.spring.auth.account.infrastructure.security

import me.spring.auth.account.application.AuthHelper
import me.spring.auth.account.application.AuthToken
import me.spring.auth.account.domain.Account

class JWTAuthHelper: AuthHelper {
    override fun authenticate(account: Account): AuthToken {
        TODO("Not yet implemented")
    }

    override fun passwdMatches(passwd1: String?, passwd2: String): Boolean {
        TODO("Not yet implemented")
    }
}