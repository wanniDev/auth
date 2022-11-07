package me.spring.auth.account.infrastructure.jwt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JWTConfig {
    @Bean
    fun jwt(jwtProperty: JWTProperty): JWT {
        return JWT(jwtProperty.issuer, jwtProperty.clientSecret, jwtProperty.expirySeconds)
    }
}