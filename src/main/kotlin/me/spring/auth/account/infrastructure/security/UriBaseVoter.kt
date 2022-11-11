package me.spring.auth.account.infrastructure.security

import org.springframework.security.access.AccessDecisionVoter
import org.springframework.security.access.AccessDecisionVoter.*
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.core.Authentication
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.util.ClassUtils
import org.springframework.util.ClassUtils.isAssignable
import javax.servlet.http.HttpServletRequest

class UriBaseVoter(private val requestMatcher: RequestMatcher): AccessDecisionVoter<FilterInvocation> {
    override fun supports(attribute: ConfigAttribute?): Boolean {
        return true
    }

    override fun supports(clazz: Class<*>?): Boolean {
        return isAssignable(FilterInvocation::class.java, clazz!!)
    }

    override fun vote(
        authentication: Authentication?,
        fi: FilterInvocation?,
        attributes: MutableCollection<ConfigAttribute>?
    ): Int {
        val request: HttpServletRequest = fi!!.request
        if (!requestMatcher.matches(request)) {
            return ACCESS_GRANTED
        }

        if (!isAssignable(JwtAuthenticationToken::class.java, authentication!!.javaClass)) {
            return ACCESS_ABSTAIN
        }

        val principal = checkNotNull(authentication.principal) as AuthInfo

        if (principal != null) {
            return ACCESS_GRANTED
        }

        return ACCESS_DENIED
    }
}