package me.spring.auth.account.application.jwt

import me.spring.auth.account.application.AccountProcessor
import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.AuthResponse
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.account.presentation.request.JoinResponse
import org.springframework.stereotype.Service

@Service
class JwtAccountFacade(private val processor: AccountProcessor) {
    fun join(joinRequest: JoinRequest): JoinResponse = processor.processJoin(joinRequest)

    fun auth(authRequest: AuthRequest): AuthResponse = processor.processAuth(authRequest)

    fun logOut(id: Long): Boolean = processor.invalidate(id)
}