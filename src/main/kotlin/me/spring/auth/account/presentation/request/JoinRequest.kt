package me.spring.auth.account.presentation.request

import me.spring.auth.common.validation.Password

data class JoinRequest(val userId: String, @field: Password(message = "Invalid password detected") val credential: String, val name: String, val email: String, val phone: String)