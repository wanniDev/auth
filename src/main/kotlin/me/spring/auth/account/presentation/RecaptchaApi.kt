package me.spring.auth.account.presentation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
class RecaptchaApi() {
    @Value("\${google.recaptcha.secret}")
    lateinit var SECRET_KEY: String
    val SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify"

    @Autowired
    var builder: RestTemplateBuilder? = null

    @RequestMapping(value = ["/validation"], method = [RequestMethod.POST])
    fun getRecaptchaResp(token: String?): String? {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val map: MultiValueMap<String, String> = LinkedMultiValueMap()
        map.add("secret", SECRET_KEY)
        map.add("response", token)
        val request: HttpEntity<MultiValueMap<String, String>> = HttpEntity<MultiValueMap<String, String>>(map, headers)
        val response = builder!!.build().postForEntity(SITE_VERIFY_URL, request, String::class.java)
        return response.body
    }
}