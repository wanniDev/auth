package me.spring.auth.account.infrastructure.security

import me.spring.auth.account.application.AuthToken
import me.spring.auth.account.domain.Account

class DummyAuthToken : AuthToken {
    private var userId: String? = null
    private var name: String? = null
    private var email: String? = null

    private constructor()

    constructor(account: Account) {
        this.userId = account.userId
        this.name = account.name
        this.email = account.email
    }
}