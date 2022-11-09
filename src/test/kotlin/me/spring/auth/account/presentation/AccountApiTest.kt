package me.spring.auth.account.presentation


import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import me.spring.auth.account.application.AccountRepositoryAdapter
import me.spring.auth.account.domain.Account
import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.common.MockMvcTest
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@MockMvcTest
internal class AccountApiTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var accountRepositoryAdapter: AccountRepositoryAdapter

    private lateinit var dummy: Account

    @BeforeEach
    fun dummyAccount() {
        dummy = accountRepositoryAdapter.save(Account("dummy123", "AQQwerDfd234#$%#4fj", "dummy", "dummy123@qwer.com", "010-234-5678"))
    }

    @Test
    @DisplayName("회원가입시 비밀번호는 영문 대소문자+특수문자+숫자가 포함되어야 한다.")
    @Throws(Exception::class)
    fun join() {
        val joinRequest = JoinRequest("wanni1234", "QweofERT#3uy#%$", "wanni", "wanni123@qwer.com", "010-123-4567")
        val reqBody = jacksonObjectMapper().writeValueAsString(joinRequest)

        mockMvc.perform(
            post("/api/v1/account/join")
                .content(reqBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("\$.response.userId").value(joinRequest.userId))
            .andExpect(jsonPath("\$.response.name").value(joinRequest.name))
            .andExpect(jsonPath("\$.response.email").value(joinRequest.email))
            .andExpect(jsonPath("\$.response.phone").value(joinRequest.phone))
    }

    @Test
    @DisplayName("회원 가입시 잘못된 조합의 비밀번호 입력시, 클라이언트 에러가 발생한다.")
    @Throws(Exception::class)
    fun join_invalid() {
        val joinRequest = JoinRequest("wanni1234", "qwer1234", "wanni", "wanni123@qwer.com", "010-123-4567")
        val reqBody = jacksonObjectMapper().writeValueAsString(joinRequest)

        mockMvc.perform(
            post("/api/v1/account/join")
                .content(reqBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError)
            .andExpect(jsonPath("\$").isNotEmpty)
    }

    @Test
    @DisplayName("로그인 테스트")
    @Throws(Exception::class)
    fun login() {
        val userId = dummy.userId ?: ""
        val password = dummy.password ?: ""

        val authRequest = AuthRequest(userId, password)
        val reqBody = jacksonObjectMapper().writeValueAsString(authRequest)

        mockMvc.perform(post("/api/v1/account/auth").content(reqBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("\$").isNotEmpty)
    }

    @Test
    @DisplayName("로그인 실패")
    @Throws(Exception::class)
    fun login_fail() {
        val userId = dummy.userId ?: ""

        val authRequest = AuthRequest(userId, "wrongPasswd")
        val reqBody = jacksonObjectMapper().writeValueAsString(authRequest)

        mockMvc.perform(post("/api/v1/account/auth").content(reqBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError)
            .andExpect(jsonPath("\$").isNotEmpty)
    }
}