package me.spring.auth.exception

enum class ErrorMsg(val msg: String) {
    DUPLICATED("Duplicated Account detected..."),
    NOT_FOUND_ACCOUNT("Cannot find member..."),
    NOT_FOUND_ACCOUNT_TOKEN("Cannot find account token for otp"),
    AUTH_FAIL("Wrong userId or password..."),
    INVALID_EMAIL("Wrong email format input detected..."),
    INVALID_EMAIL_TOKEN("Invalid or cannot found email..."),
    INVALID_OTP_CODE("Invalid OTP code detected...");
}