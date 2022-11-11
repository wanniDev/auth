package me.spring.auth.account.application.email

import org.springframework.stereotype.Component

@Component
class ConsoleEmailSender: EmailSender {
    override fun send(emailMessage: EmailMessage) {
        println("test email sent from: ${emailMessage.from}, to: ${emailMessage.to}")
    }
}