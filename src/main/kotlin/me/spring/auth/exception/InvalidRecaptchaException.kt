package me.spring.auth.exception

class InvalidRecaptchaException : RuntimeException {
    constructor() : super(ErrorMsg.INVALID_RECAPTCHA_RESP.msg)
    constructor(message: String?) : super(message)
}