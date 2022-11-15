package me.spring.auth.account.presentation

import me.spring.auth.account.application.otp.OTPFacade
import me.spring.auth.account.infrastructure.security.AuthInfo
import me.spring.auth.account.presentation.request.OTPRequest
import me.spring.auth.account.presentation.request.QRCodeResponse
import me.spring.auth.common.api.ApiResult
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("api")
class TwoFactorAuthApi(private val otpFacade: OTPFacade) {
    @PostMapping("v1/otp")
    fun generateQrCode(@AuthenticationPrincipal authInfo: AuthInfo): ApiResult<QRCodeResponse> {
        val qrCodeImgBytes = otpFacade.processQRCode(authInfo)

        return ApiResult.OK(QRCodeResponse(qrCodeImgBytes, LocalDateTime.now()))
    }

    @PostMapping("v1/otp/valid")
    fun validateOTP(@AuthenticationPrincipal authInfo: AuthInfo, @RequestBody otpRequest: OTPRequest): ResponseEntity<Boolean> {
        val result = otpFacade.processValid(authInfo.id, otpRequest)
        return ResponseEntity.ok(result)
    }
}