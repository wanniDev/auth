package me.spring.auth.exception

class NotFoundAccountException : RuntimeException {
    constructor() : super(ErrorMsg.NOT_FOUND_ACCOUNT.msg)
    constructor(message: String?) : super(message)
}