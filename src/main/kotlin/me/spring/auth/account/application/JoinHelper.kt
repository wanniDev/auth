package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import me.spring.auth.account.infrastructure.security.Role
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.exception.DuplicateAccountException

interface JoinHelper {

    fun produceNewAccount(joinRequest: JoinRequest): Account {

        return Account(joinRequest.userId, joinRequest.credential, joinRequest.name, joinRequest.email, joinRequest.phone)
    }
    fun produceNewToken(account: Account, vararg roles: String?): String

    fun produceUserToken(account: Account): String {
        return produceNewToken(account, Role.USER.value)
    }

    fun produceAdminToken(account: Account): String {
        return produceNewToken(account, Role.USER.value, Role.ADMIN.value)
    }

    fun checkDuplicated(isDuplicated: Boolean) {
        if (isDuplicated) {
            throw DuplicateAccountException()
        }
    }
}