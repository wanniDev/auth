package me.spring.auth.account.presentation

import me.spring.auth.account.application.jwt.JwtAccountFacade
import me.spring.auth.account.infrastructure.security.AuthInfo
import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.AuthResponse
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.account.presentation.request.JoinResponse
import me.spring.auth.common.api.ApiResult
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("api")
class AccountApi(private val jwtAccountFacade: JwtAccountFacade, @Qualifier("adminAuthenticationManager") private val authenticationManager: AuthenticationManager) {

    @PostMapping("v1/account/join")
    fun join(@Valid @RequestBody joinRequest: JoinRequest): ApiResult<JoinResponse> {
        val joinResponse = jwtAccountFacade.join(joinRequest)
        return ApiResult.OK(joinResponse)
    }

    @PostMapping("v1/account/auth")
    fun auth(@Valid @RequestBody authRequest: AuthRequest): ApiResult<AuthResponse> {
        val auth = jwtAccountFacade.auth(authRequest)
        val ok = ApiResult.OK(auth)
        return ok;
    }

    @PostMapping("v1/account/logout")
    fun logout(@AuthenticationPrincipal authInfo: AuthInfo): ResponseEntity<Boolean> {
        val logout = jwtAccountFacade.logOut(authInfo.id)
        return ResponseEntity.ok(logout)
    }
}