package me.spring.auth.account.presentation

import me.spring.auth.account.application.AccountFacade
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
class AccountJoinApi(private val accountFacade: AccountFacade) {

    @PostMapping("v1/account/join")
    fun join(@Valid @RequestBody joinRequest: JoinRequest): ApiResult<JoinResponse> {
        val account = accountFacade.join(joinRequest)
        return ApiResult.OK(JoinResponse(account))
    }
}