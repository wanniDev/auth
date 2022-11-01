package me.spring.auth.account.presentation.request

import java.time.LocalDateTime

data class JoinResponse(val userId: String, val name: String, val email: String, val phone: String, val createTime: LocalDateTime)
