package me.spring.auth.account.application

import me.spring.auth.account.presentation.request.JoinRequest

class AccountFacade(private val processor: AccountProcessor) {
    fun join(joinRequest: JoinRequest) = processor.processJoin(joinRequest)
}