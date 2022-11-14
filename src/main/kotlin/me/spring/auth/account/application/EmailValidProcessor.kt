package me.spring.auth.account.application

import java.util.regex.Pattern

interface EmailValidProcessor {
    fun processSendValid(req: Map<String, String>): Boolean

    fun processValidSend(token: String, email: String): Boolean

    fun isEmailFormat(email: String): Boolean {
        return Pattern.matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+", email)
    }
}