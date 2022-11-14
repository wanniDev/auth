package me.spring.auth.account.application.email

import me.spring.auth.account.application.EmailValidProcessor
import org.springframework.stereotype.Service

@Service
class EmailFacade(private val processor: EmailValidProcessor) {
    fun sendValidation(emailReq: Map<String, String>): Boolean = processor.processSendValid(emailReq)
    fun getValidResult(token: String, email: String): Boolean = processor.processValidSend(token, email)
}