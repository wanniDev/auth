package me.spring.auth.account.presentation.request

import me.spring.auth.account.domain.Account
import java.time.LocalDateTime

class JoinResponse {
    var apiToken: String? = null
        private set
    var userId: String? = null
        private set
    var name: String? = null
        private set
    var email: String? = null
        private set
    var phone: String? = null
        private set
    var createTime: LocalDateTime? = null
        private set

    private constructor() {}

    constructor(userId: String?, name: String?, email: String?, phone: String?, createTime: LocalDateTime?) {
        this.userId = userId
        this.name = name
        this.email = email
        this.phone = phone
        this.createTime = createTime
    }

    constructor(apiToken:String?, userId: String?, name: String?, email: String?, phone: String?, createTime: LocalDateTime?) {
        this.apiToken = apiToken
        this.userId = userId
        this.name = name
        this.email = email
        this.phone = phone
        this.createTime = createTime
    }

    constructor(apiToken:String, account: Account) {
        this.apiToken = apiToken
        this.userId = account.userId
        this.name = account.name
        this.email = account.email
        this.phone = account.phone
        this.createTime = account.createTime
    }

    constructor(account: Account) {
        userId = account.userId
        name = account.name
        email = account.email
        phone = account.phone
        createTime = account.createTime
    }
}