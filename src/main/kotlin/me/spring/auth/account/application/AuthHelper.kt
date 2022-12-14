package me.spring.auth.account.application

import me.spring.auth.account.application.email.EmailMessage
import me.spring.auth.account.domain.Account


interface AuthHelper {
    fun authenticate(account: Account) :AuthToken
    fun passwdMatches(raw: String?, encoded: String?): Boolean
    fun validateMail(emailMessage: EmailMessage)
    fun invalidate(id: Long): Boolean
}