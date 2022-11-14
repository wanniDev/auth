package me.spring.auth.account.application.email.console

import me.spring.auth.account.application.EmailValidProcessor
import me.spring.auth.account.application.EnrollUserManager
import me.spring.auth.account.application.email.EmailMessage
import me.spring.auth.account.application.email.EmailSender
import me.spring.auth.exception.InvalidEmailFormatException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ConsoleEmailValidProcessor(private val emailSender: EmailSender,
                                 private val enrollUserManager: EnrollUserManager
): EmailValidProcessor {
    override fun processValid(req: Map<String, String>): Boolean {
        val from = "no-reply@host.com"
        val to = req["email"]!!
        if (!super.isEmailFormat(to))
            throw InvalidEmailFormatException()
        val token = UUID.randomUUID().toString()
        emailSender.send(EmailMessage(from, to, "/api/v1/email?token=$token&email=$to"))
        return enrollUserManager.enroll(token)
    }
}