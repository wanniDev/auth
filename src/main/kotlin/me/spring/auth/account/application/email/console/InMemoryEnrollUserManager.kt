package me.spring.auth.account.application.email.console

import me.spring.auth.account.application.EnrollUserManager
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentSkipListSet

@Component
class InMemoryEnrollUserManager: EnrollUserManager {
    private val pendingTokens = ConcurrentSkipListSet<String>()

    override fun enroll(token: String): Boolean {
        if (pendingTokens.size > 1000)
            pendingTokens.clear()
        return pendingTokens.add(token)
    }

    override fun isPending(token: String): Boolean {
        val contains = pendingTokens.contains(token)
        if (contains)
            pendingTokens.remove(token)
        return contains
    }
}