package me.spring.auth.account.application.admin

import me.spring.auth.account.application.JoinHelper
import me.spring.auth.account.domain.Account
import me.spring.auth.account.presentation.request.JoinRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Qualifier("adminJoinHelper")
class AdminJoinHelper(private val passwordEncoder: PasswordEncoder): JoinHelper {
    override fun produceNewAccount(joinRequest: JoinRequest): Account {
        val produceNewAccount = super.produceNewAccount(joinRequest)
        produceNewAccount.encodePasswd(passwordEncoder)
        return produceNewAccount
    }


    override fun produceNewToken(account: Account, vararg roles: String?): String {
        return ""
    }
}