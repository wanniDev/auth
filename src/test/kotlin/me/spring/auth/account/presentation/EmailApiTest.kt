package me.spring.auth.account.presentation

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import me.spring.auth.common.MockMvcTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@MockMvcTest
class EmailApiTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("검증 이메일 전송 테스트")
    @Throws(Exception::class)
    fun send() {
        val reqBody = jacksonObjectMapper().writeValueAsString(hashMapOf(Pair("email", "wanni123@fakemail.com")))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/email/valid")
                .content(reqBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @DisplayName("이메일의 입력 방식을 지키지 않으면, 클라이언트 에러가 발생한다.")
    @Throws(Exception::class)
    fun send_invalid() {
        val reqBody = jacksonObjectMapper().writeValueAsString(hashMapOf(Pair("email", "wanni123@fakemailcom")))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/email/valid")
                .content(reqBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }
}