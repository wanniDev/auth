package me.spring.auth.account.application.admin

import me.spring.auth.account.application.AuthBlockManager
import me.spring.auth.account.application.AuthHelper
import me.spring.auth.account.application.AuthToken
import me.spring.auth.account.application.email.EmailMessage
import me.spring.auth.account.domain.Account
import me.spring.auth.account.infrastructure.security.JwtAuthenticationToken
import me.spring.auth.exception.NotFoundAccountException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Qualifier("adminAuthHelper")
class AdminAuthHelper(@Qualifier("adminAuthenticationManager") private val authenticationManager: AuthenticationManager,
                      private val passwordEncoder: PasswordEncoder,
                      private val authBlockManager: AuthBlockManager<Long>
) : AuthHelper {
    override fun authenticate(account: Account): AuthToken {
        val authToken = JwtAuthenticationToken(account, "")
        val authenticate = authenticationManager.authenticate(authToken)
        SecurityContextHolder.getContext().authentication = authenticate
        return authenticate as AuthToken
    }

    override fun passwdMatches(raw: String?, encoded: String?): Boolean {
        return passwordEncoder.matches(raw, encoded)
    }

    override fun invalidate(id: Long): Boolean {
        if(!authBlockManager.existsById(id)) {
            return authBlockManager.add(id)
        }
        throw NotFoundAccountException("Already invalidated token...")
    }

    override fun validateMail(emailMessage: EmailMessage) {
        TODO("Not yet implemented")
    }
}