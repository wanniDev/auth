package me.spring.auth.exception

class AccountAuthException : RuntimeException {
    constructor() : super(AccountExceptionMsg.AUTH_FAIL.msg)
    constructor(message: String?) : super(message)
}