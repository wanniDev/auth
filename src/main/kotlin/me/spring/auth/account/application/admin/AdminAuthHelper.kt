package me.spring.auth.account.application.admin

import me.spring.auth.account.application.AuthHelper
import me.spring.auth.account.application.AuthToken
import me.spring.auth.account.domain.Account
import me.spring.auth.account.infrastructure.security.JwtAuthenticationToken
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Qualifier("adminAuthHelper")
class AdminAuthHelper(@Qualifier("adminAuthenticationManager") private val authenticationManager: AuthenticationManager, private val passwordEncoder: PasswordEncoder) : AuthHelper {
    override fun authenticate(account: Account): AuthToken {
        val authToken = JwtAuthenticationToken(account, "")
        val authenticate = authenticationManager.authenticate(authToken)
        SecurityContextHolder.getContext().authentication = authenticate
        return authenticate as AuthToken
    }

    override fun passwdMatches(raw: String?, encoded: String?): Boolean {
        return passwordEncoder.matches(raw, encoded)
    }
}