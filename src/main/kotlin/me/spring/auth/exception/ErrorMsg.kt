package me.spring.auth.exception

enum class ErrorMsg(val msg: String) {
    DUPLICATED("Duplicated Account detected..."),
    NOT_FOUND("Cannot find member..."),
    AUTH_FAIL("Wrong userId or password..."),
    INVALID_EMAIL("Wrong email format input detected...")
}