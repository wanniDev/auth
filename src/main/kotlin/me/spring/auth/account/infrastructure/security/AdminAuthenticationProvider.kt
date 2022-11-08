package me.spring.auth.account.infrastructure.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.util.ClassUtils

class AdminAuthenticationProvider: AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        println("catch admin authentication provider!!!")
        return JwtAuthenticationToken("", "")
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return ClassUtils.isAssignable(JwtAuthenticationToken::class.java, authentication!!)
    }
}