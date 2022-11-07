package me.spring.auth.account.application.dummy

import me.spring.auth.account.application.AuthHelper
import me.spring.auth.account.application.AuthToken
import me.spring.auth.account.domain.Account

class DummyAuthHelper : AuthHelper {
    override fun authenticate(account: Account): AuthToken {
        return DummyAuthToken(account)
    }

    override fun passwdMatches(raw: String?, encoded: String?): Boolean {
        return raw.equals(encoded)
    }
}