package me.spring.auth.exception

class NotFoundAccountException : RuntimeException {
    constructor() : super(AccountExceptionMsg.NOT_FOUND.msg)
    constructor(message: String?) : super(message)
}