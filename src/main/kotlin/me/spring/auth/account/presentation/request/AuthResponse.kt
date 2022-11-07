package me.spring.auth.account.presentation.request

import me.spring.auth.account.application.AuthToken

class AuthResponse {

    private var session: Any? = null

    private constructor()

    constructor(session: Any) {
        this.session = session
    }
}
