package me.spring.auth.account.application.dummy

import me.spring.auth.account.application.JoinHelper
import me.spring.auth.account.domain.Account

class DummyJoinHelper: JoinHelper {
    override fun produceNewToken(account: Account): String {
        return account.toString()
    }
}