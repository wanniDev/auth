package me.spring.auth.account.infrastructure.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "jwt.token")
class JWTProperty {
    lateinit var header: String
    lateinit var issuer: String
    lateinit var clientSecret: String
    var expirySeconds = 0
    override fun toString(): String {
        return "JWTConfigure{" +
                "header='" + header + '\'' +
                ", issuer='" + issuer + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", expirySeconds=" + expirySeconds +
                '}'
    }
}