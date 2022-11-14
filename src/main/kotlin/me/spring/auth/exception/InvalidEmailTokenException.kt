package me.spring.auth.exception

class InvalidEmailTokenException: RuntimeException {
    constructor() : super(ErrorMsg.INVALID_EMAIL_TOKEN.msg)
    constructor(message: String?) : super(message)
}