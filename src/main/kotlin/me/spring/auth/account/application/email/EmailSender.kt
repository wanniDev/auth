package me.spring.auth.account.application.email

interface EmailSender {
    fun send(emailMessage: EmailMessage)
}