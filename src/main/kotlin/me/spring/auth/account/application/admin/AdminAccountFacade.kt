package me.spring.auth.account.application.admin

import me.spring.auth.account.application.AccountProcessor
import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.JoinRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class AdminAccountFacade(@Qualifier("adminAccountProcessor") private val processor: AccountProcessor) {
    fun join(joinRequest: JoinRequest) = processor.processJoin(joinRequest)

    fun auth(authRequest: AuthRequest) = processor.processAuth(authRequest)
}