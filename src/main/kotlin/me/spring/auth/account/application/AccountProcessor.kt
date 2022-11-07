package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.AuthResponse
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.account.presentation.request.JoinResponse

interface AccountProcessor {
    fun processJoin(joinRequest: JoinRequest): JoinResponse
    fun processAuth(authRequest: AuthRequest): AuthResponse
}