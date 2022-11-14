package me.spring.auth.account.application.otp

import me.spring.auth.account.infrastructure.security.AuthInfo
import org.springframework.stereotype.Service

@Service
class OTPFacade(private val processor: OTPProcessor) {
    fun processQRCode(authInfo: AuthInfo) = processor.supplyQRCode(authInfo)
}