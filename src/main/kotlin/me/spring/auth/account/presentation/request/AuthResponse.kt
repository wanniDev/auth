package me.spring.auth.account.presentation.request

import me.spring.auth.account.application.AuthToken

class AuthResponse {

    private var session: AuthToken? = null

    private constructor()

    constructor(session: AuthToken) {
        this.session = session
    }
}
