package me.spring.auth.account.application

import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.JoinRequest
import org.springframework.stereotype.Service

@Service
class AccountFacade(private val processor: AccountProcessor) {
    fun join(joinRequest: JoinRequest) = processor.processJoin(joinRequest)

    fun auth(authRequest: AuthRequest) = processor.processAuth(authRequest)
}