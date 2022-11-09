package me.spring.auth.account.presentation

import me.spring.auth.account.application.admin.AdminAccountFacade
import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.AuthResponse
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.account.presentation.request.JoinResponse
import me.spring.auth.common.api.ApiResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("api")
class AdminApi(private val adminAccountFacade: AdminAccountFacade) {
    @PostMapping("v1/account/admin/join")
    fun join(@Valid @RequestBody joinRequest: JoinRequest): ApiResult<JoinResponse> {
        val joinResponse = adminAccountFacade.join(joinRequest)
        return ApiResult.OK(joinResponse)
    }

    @PostMapping("v1/account/admin/auth")
    fun auth(@Valid @RequestBody authRequest: AuthRequest): ApiResult<AuthResponse> {
        val auth = adminAccountFacade.auth(authRequest)
        return ApiResult.OK(auth);
    }
}