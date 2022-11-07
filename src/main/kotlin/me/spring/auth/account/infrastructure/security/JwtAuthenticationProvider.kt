package me.spring.auth.account.infrastructure.security

import me.spring.auth.account.application.AccountRepositoryAdapter
import me.spring.auth.account.domain.Account
import me.spring.auth.account.infrastructure.jwt.JWT
import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.AuthResponse
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils.createAuthorityList
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.ClassUtils

class JwtAuthenticationProvider(private val jwt: JWT): AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        val authenticationToken = authentication as JwtAuthenticationToken
        val account = authenticationToken.principal as Account
        val authenticated = JwtAuthenticationToken(account.id!!, null, createAuthorityList("ROLE_USER"))
        val token: String = account.newApiToken(jwt, arrayOf("ROLE_USER"))
        authenticated.details = AuthResponse(token)
        return authenticated
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return ClassUtils.isAssignable(JwtAuthenticationToken::class.java, authentication!!)
    }
}