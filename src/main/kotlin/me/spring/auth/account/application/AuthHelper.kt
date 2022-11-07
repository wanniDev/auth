package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import org.springframework.stereotype.Component


interface AuthHelper {
    fun authenticate(account: Account) :AuthToken
    fun passwdMatches(raw: String?, encoded: String?): Boolean
}