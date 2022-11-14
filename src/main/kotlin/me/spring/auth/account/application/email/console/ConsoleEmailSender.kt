package me.spring.auth.account.application.email.console

import me.spring.auth.account.application.email.EmailMessage
import me.spring.auth.account.application.email.EmailSender
import org.springframework.stereotype.Component

@Component
class ConsoleEmailSender: EmailSender {
    override fun send(emailMessage: EmailMessage) {
        println("test email sent from: ${emailMessage.from}, to: ${emailMessage.to} and message: ${emailMessage.message}")
    }
}