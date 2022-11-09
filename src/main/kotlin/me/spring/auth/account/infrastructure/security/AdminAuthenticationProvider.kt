package me.spring.auth.account.infrastructure.security

import me.spring.auth.account.domain.Account
import me.spring.auth.account.infrastructure.jwt.JWT
import me.spring.auth.account.presentation.request.AuthResponse
import me.spring.auth.exception.NotFoundAccountException
import org.springframework.dao.DataAccessException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.util.ClassUtils

class AdminAuthenticationProvider(private val jwt: JWT): AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        try {
            val authenticationToken = authentication as JwtAuthenticationToken
            val account = authenticationToken.principal as Account
            val authenticated = JwtAuthenticationToken(account.id!!, null,
                AuthorityUtils.createAuthorityList("ROLE_USER")
            )
            val token: String = account.newApiToken(jwt, arrayOf("ROLE_USER", "ROLE_ADMIN"))
            authenticated.details = AuthResponse(token)
            return authenticated
        } catch (e: NotFoundAccountException) {
            throw UsernameNotFoundException(e.message)
        } catch (e: IllegalArgumentException) {
            throw BadCredentialsException(e.message)
        } catch (e: DataAccessException) {
            throw AuthenticationServiceException(e.message)
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return ClassUtils.isAssignable(JwtAuthenticationToken::class.java, authentication!!)
    }
}