package me.spring.auth.exception

enum class AccountExceptionMsg(val msg: String) {
    DUPLICATED("Duplicated Account detected..."),
    NOT_FOUND("Cannot find member...")
}