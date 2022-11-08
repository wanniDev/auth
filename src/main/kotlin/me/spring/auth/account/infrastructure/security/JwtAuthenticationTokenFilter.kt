package me.spring.auth.account.infrastructure.security

import me.spring.auth.account.infrastructure.jwt.JWT
import me.spring.auth.account.infrastructure.jwt.JWTProperty
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.GenericFilterBean
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.regex.Pattern
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationTokenFilter(private val jwtProperty: JWTProperty, private val jwt: JWT): GenericFilterBean() {
    private val BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE)

    override fun doFilter(req: ServletRequest?, res: ServletResponse?, chain: FilterChain?) {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse

        if (SecurityContextHolder.getContext().authentication == null) {
            val currToken = obtainAuthTokenFrom(req)
            if (currToken != null) {
                try {
                    val claims = jwt.verify(currToken)

                    // TODO wall clocked issue 해결하기
                    if (isRefreshable(claims)) {
                        val refreshToken = jwt.refreshToken(currToken)
                        res.setHeader(jwtProperty.header, refreshToken)
                    }

                    val accountKey = checkNotNull(claims.accountKey)
                    val name = checkNotNull(claims.name)
                    val email = checkNotNull(claims.email)

                    val authorities = obtainAuthorities(claims)

                    if (name.isNotEmpty() && email.isNotEmpty() && authorities.isNotEmpty()) {
                        val auth =
                            JwtAuthenticationToken(AuthId(accountKey, name, email), null, authorities)
                        auth.details = WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = auth
                    }

                } catch (e: Exception) {
                    // TODO error log
                    e.message
                }
            }
            // TODO 시간 체크 후, 특정 시간밖에 안남으면 refresh
        } else {
            SecurityContextHolder.getContext().authentication
        }
        chain!!.doFilter(request, response)
    }

    private fun obtainAuthorities(claims: JWT.Claims): List<GrantedAuthority> {
        val roles = claims.roles
        return if (roles.isNullOrEmpty()) emptyList() else
            roles.iterator().asSequence()
                .map {
                    SimpleGrantedAuthority(it)
                }.toList()
    }

    private fun isRefreshable(claims: JWT.Claims): Boolean {
        val exp = claims.exp()
        if (exp > 0) {
            val remain = exp - System.currentTimeMillis()
            val refreshMilliSeconds = jwtProperty.refreshSeconds * 1000
            return remain < refreshMilliSeconds
        }
        return false
    }

    private fun obtainAuthTokenFrom(req: HttpServletRequest): String? {
        var apiKey = req.getHeader(jwtProperty.header)
        if (apiKey != null) {
            try {
                apiKey = URLDecoder.decode(apiKey, "UTF-8")
                val parts = apiKey.split(" ")
                if (parts.size == 2) {
                    val scheme = parts[0]
                    val credentials = parts[1]
                    return if (BEARER.matcher(scheme).matches()) credentials else null
                }
            } catch (e: UnsupportedEncodingException) {
                // TODO error log
                e.message
            }
        }
        return null
    }
}