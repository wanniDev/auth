package me.spring.auth.exception

class InvalidEmailFormatException : RuntimeException {
    constructor() : super(ErrorMsg.INVALID_EMAIL.msg)
    constructor(message: String?) : super(message)
}