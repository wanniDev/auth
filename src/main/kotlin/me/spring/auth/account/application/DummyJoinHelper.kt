package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import org.springframework.stereotype.Component
import java.util.UUID

class DummyJoinHelper: JoinHelper {
    override fun produceNewToken(account: Account): String {
        return account.toString()
    }
}