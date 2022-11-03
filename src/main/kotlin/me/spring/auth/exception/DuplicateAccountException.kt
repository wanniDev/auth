package me.spring.auth.exception

class DuplicateAccountException : RuntimeException {
    constructor() : super(AccountExceptionMsg.DUPLICATED.msg)
    constructor(message: String?) : super(message)
}