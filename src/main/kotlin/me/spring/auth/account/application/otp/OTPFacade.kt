package me.spring.auth.account.application.otp

import me.spring.auth.account.infrastructure.security.AuthInfo
import me.spring.auth.account.presentation.request.OTPRequest
import org.springframework.stereotype.Service

@Service
class OTPFacade(private val processor: OTPProcessor) {
    fun processQRCode(authInfo: AuthInfo): ByteArray = processor.supplyQRCode(authInfo)
    fun processValid(accountTokenId: Long, otpRequest: OTPRequest): Boolean = processor.valid(accountTokenId, otpRequest)
}