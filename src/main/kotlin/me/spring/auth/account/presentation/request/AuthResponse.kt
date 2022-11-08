package me.spring.auth.account.presentation.request

class AuthResponse {

    private var apiToken: Any? = null

    private constructor()

    constructor(session: Any) {
        this.apiToken = session
    }
}
