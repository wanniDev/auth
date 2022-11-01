package me.spring.auth.account.application

import me.spring.auth.account.presentation.request.JoinRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AccountProcessorTest {
    @Autowired
    var accountProcessor: AccountProcessor? = null
    @Test
    @Throws(Exception::class)
    fun processJoin() {
        val account = accountProcessor!!.processJoin(
            JoinRequest("wanni1234", "sdfwer!@Efdf", "wanni", "wanni123@qwer.com", "010-123-4567")
        )

        Assertions.assertThat(account).isNotNull
    }
}