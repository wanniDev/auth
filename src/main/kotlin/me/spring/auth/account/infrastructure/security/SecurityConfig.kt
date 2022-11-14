package me.spring.auth.account.infrastructure.security

import me.spring.auth.account.infrastructure.jwt.JWT
import me.spring.auth.account.infrastructure.jwt.JWTProperty
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.access.AccessDecisionManager
import org.springframework.security.access.AccessDecisionVoter
import org.springframework.security.access.vote.UnanimousBased
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.WebExpressionVoter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.RegexRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import java.util.regex.Pattern

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class SecurityConfig {

    @Bean
    @Primary
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Qualifier("otpUserKeyEncoder")
    fun otpUserKeyEncoder(): PasswordEncoder {
        val pbkdf2PasswordEncoder = Pbkdf2PasswordEncoder()
        pbkdf2PasswordEncoder.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256)
        return pbkdf2PasswordEncoder
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
    @Primary
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
    fun accessDecisionManager(): AccessDecisionManager {
        val decisionVoters: MutableList<AccessDecisionVoter<*>> = ArrayList()
        decisionVoters.add(WebExpressionVoter())
        decisionVoters.add(uriBasedVoter())
        // 모든 voter가 승인해야 한다.
        return UnanimousBased(decisionVoters)
    }

    private fun uriBasedVoter(): UriBaseVoter {
        val regex = "^/api/v1/account/[a-zA-z\\d]*"
        val pattern = Pattern.compile(regex)
        val requiresAuthorizationRequestMatcher: RequestMatcher = RegexRequestMatcher(pattern.pattern(), null)
        return UriBaseVoter(requiresAuthorizationRequestMatcher)
    }

    @Bean
    @Qualifier("adminAuthenticationManager")
    fun adminAuthenticationManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authenticationManagerBuilder.authenticationProvider(adminAuthenticationProvider())
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun adminAuthenticationProvider(): AdminAuthenticationProvider {
        return AdminAuthenticationProvider(jwt())
    }

    @Bean
    fun filterChain(http: HttpSecurity, defaultAuthManager: AuthenticationManager, @Qualifier("adminAuthenticationManager") adminAuthManager: AuthenticationManager): SecurityFilterChain {
        http.csrf().disable()
        http.headers().disable()
        http.authorizeRequests()
            .antMatchers("/api/v1/account/logout")
            .hasRole(Role.USER.value)
//            .accessDecisionManager(accessDecisionManager())
            .anyRequest().permitAll()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authenticationManager(defaultAuthManager)
        http.authenticationManager(adminAuthManager)

        http.formLogin().disable()
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}