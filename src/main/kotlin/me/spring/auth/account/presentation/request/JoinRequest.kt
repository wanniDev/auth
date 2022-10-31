package me.spring.auth.account.presentation.request

data class JoinRequest(val userId: String, val credential: String, val name: String, val email: String, val phone: String)