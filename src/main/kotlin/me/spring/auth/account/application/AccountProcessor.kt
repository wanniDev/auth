package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.common.Commander
import me.spring.auth.exception.DuplicateMemberException
import org.springframework.stereotype.Component

@Component
class AccountProcessor(private val accountRepository: AccountRepositoryAdapter) {
    fun processJoin(joinRequest: JoinRequest): Account = processJoin(joinRequest) {
        arg -> val userId = joinRequest.userId
        val email = arg.email
        if (isDuplicated(userId, email))
            throw DuplicateMemberException("UserId [$userId], email [$email] already exisits..")

        val account = Account(arg.userId, arg.credential, arg.name, arg.email, arg.phone)

        accountRepository.save(account)
    }

    private fun processJoin(joinRequest: JoinRequest, commander:(JoinRequest) -> Account): Account {
        return commander.invoke(joinRequest)
    }

    private fun isDuplicated(userId: String, email: String): Boolean =
        accountRepository.existByUserId(userId) && accountRepository.existByEmail(email)
}