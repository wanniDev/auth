package me.spring.auth.account.infrastructure.security

import me.spring.auth.account.infrastructure.jwt.JWT
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.util.regex.Pattern
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationTokenFilter(private val header: String, private val jwt: JWT): GenericFilterBean() {
    private val BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE)

    override fun doFilter(req: ServletRequest?, res: ServletResponse?, chain: FilterChain?) {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse

        if (SecurityContextHolder.getContext().authentication == null) {
            // TODO 헤더에 토큰 검증
            // TODO 시간 체크 후, 특정 시간밖에 안남으면 refresh
        } else {
            SecurityContextHolder.getContext().authentication
        }
        chain!!.doFilter(request, response)
    }
}