package me.spring.auth.exception

class InvalidOTPCodeException : RuntimeException {
    constructor() : super(ErrorMsg.INVALID_OTP_CODE.msg)
    constructor(message: String?) : super(message)
}