package me.spring.auth.account.application

import me.spring.auth.account.infrastructure.jwt.JWT
import me.spring.auth.account.presentation.request.JoinRequest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AccountProcessorTest {
    @Autowired
    lateinit var accountProcessor: AccountProcessor

    @Autowired
    lateinit var jwt: JWT

    @Test
    @DisplayName("회원가입 기본 비즈니스 로직 테스트")
    @Throws(Exception::class)
    fun processJWTJoin() {
        val joinRequest = JoinRequest("wanni1234", "sdfwer!@Efdf", "wanni", "wanni123@qwer.com", "010-123-4567")
        val account = accountProcessor.processJoin(
            joinRequest
        )

        val newToken = jwt.newToken(JWT.Claims.of(account.id, account.name, account.email, arrayOf("ROLE_USER")))
        val claims = jwt.verify(newToken)

        assertThat(claims.accountKey).isEqualTo(account.id)
        assertThat(claims.email).isEqualTo(account.email)
        assertThat(claims.name).isEqualTo(account.name)
    }
}