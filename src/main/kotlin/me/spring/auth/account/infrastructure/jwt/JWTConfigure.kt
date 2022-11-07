package me.spring.auth.account.infrastructure.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt.token")
class JWTConfigure {
    var header: String? = null
    var issuer: String? = null
    var clientSecret: String? = null
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