package me.spring.auth.exception

class NotFoundAccountException : RuntimeException {
    constructor() : super(ErrorMsg.NOT_FOUND.msg)
    constructor(message: String?) : super(message)
}