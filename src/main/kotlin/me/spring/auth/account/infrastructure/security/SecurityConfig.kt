package me.spring.auth.account.infrastructure.security

import me.spring.auth.account.infrastructure.jwt.JWT
import me.spring.auth.account.infrastructure.jwt.JWTProperty
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtProperty(): JWTProperty {
        return JWTProperty()
    }

    @Bean
    fun jwt(): JWT {
        val jwtProperty = jwtProperty()
        return JWT(jwtProperty.issuer, jwtProperty.clientSecret, jwtProperty.expirySeconds)
    }
    @Bean
    fun jwtAuthenticationProvider(): JwtAuthenticationProvider? {
        return JwtAuthenticationProvider(jwt())
    }

    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider())
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun jwtAuthenticationTokenFilter(): JwtAuthenticationTokenFilter {
        return JwtAuthenticationTokenFilter(jwtProperty(), jwt())
    }

    @Bean
    fun filterChain(http: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain {
        http.csrf().disable()

        http.authorizeRequests().antMatchers("/api/v1/account/auth", "/api/v1/account/join").permitAll()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.authenticationManager(authenticationManager)

        http.formLogin().disable()

        return http.build()
    }
}