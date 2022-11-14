package me.spring.auth.exception

class DuplicateAccountException : RuntimeException {
    constructor() : super(ErrorMsg.DUPLICATED.msg)
    constructor(message: String?) : super(message)
}