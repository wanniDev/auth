package me.spring.auth.account.presentation

import me.spring.auth.account.application.email.EmailFacade
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class EmailApi(private val emailFacade: EmailFacade) {
    @PostMapping("v1/email/valid")
    fun sendEmail(@RequestBody emailReq: Map<String, String>): ResponseEntity<Boolean> {
        return ResponseEntity.ok(emailFacade.sendValidation(emailReq))
    }

    @GetMapping("v1/email/valid")
    fun checkEmail(token: String, email: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(emailFacade.getValidResult(token, email))
    }
}