package me.spring.auth.account.presentation

import me.spring.auth.account.application.otp.OTPFacade
import me.spring.auth.account.infrastructure.security.AuthInfo
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class TwoFactorAuthApi(private val OTPFacade: OTPFacade) {
    @PostMapping("v1/otp")
    fun generateQrCode(@AuthenticationPrincipal authInfo: AuthInfo) {

    }
}