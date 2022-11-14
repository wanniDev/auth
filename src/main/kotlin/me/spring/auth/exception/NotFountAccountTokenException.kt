package me.spring.auth.exception

class NotFountAccountTokenException : RuntimeException {
    constructor() : super(ErrorMsg.NOT_FOUND_ACCOUNT_TOKEN.msg)
    constructor(message: String?) : super(message)
}