package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import me.spring.auth.account.presentation.request.JoinRequest

interface JoinHelper {

    fun produceNewAccount(joinRequest: JoinRequest): Account {
        return Account(joinRequest.userId, joinRequest.credential, joinRequest.name, joinRequest.email, joinRequest.phone)
    }

    fun produceNewToken(account: Account): String
}