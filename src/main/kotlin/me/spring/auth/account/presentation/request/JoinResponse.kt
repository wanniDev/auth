package me.spring.auth.account.presentation.request

import me.spring.auth.account.domain.Account
import java.time.LocalDateTime

class JoinResponse {
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

    constructor(member: Account) {
        userId = member.userId
        name = member.name
        email = member.email
        phone = member.phone
        createTime = member.createTime
    }
}