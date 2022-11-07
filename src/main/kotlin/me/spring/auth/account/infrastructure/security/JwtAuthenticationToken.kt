package me.spring.auth.account.infrastructure.security

import me.spring.auth.account.application.AuthToken
import me.spring.auth.account.domain.Account
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken : AbstractAuthenticationToken, AuthToken {
    private var principal: Any ?= null
    private var credentials: String ?= null

    constructor(principal: Account, credentials: String) : super(null) {
        super.setAuthenticated(false)
        this.principal = principal
        this.credentials = credentials
    }

    constructor(principal: String, credentials: String) : super(null) {
        super.setAuthenticated(false)
        this.principal = principal
        this.credentials = credentials
    }

    internal constructor(principal: Any, credentials: String?, authorities: Collection<GrantedAuthority?>?) : super(
        authorities
    ) {
        super.setAuthenticated(true)
        this.principal = principal
        this.credentials = credentials
    }

    override fun getPrincipal(): Any? {
        return principal
    }

    override fun getCredentials(): String? {
        return credentials
    }
}