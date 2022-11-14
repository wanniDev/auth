package me.spring.auth.exception

class AccountAuthException : RuntimeException {
    constructor() : super(ErrorMsg.AUTH_FAIL.msg)
    constructor(message: String?) : super(message)
}