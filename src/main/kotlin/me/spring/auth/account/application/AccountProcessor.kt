package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.common.Commander
import me.spring.auth.exception.DuplicateMemberException

class AccountProcessor(private val accountRepository: AccountRepositoryAdapter) {
    fun processJoin(joinRequest: JoinRequest): Account = processJoin(joinRequest, object :
        Commander<Account, JoinRequest> {
        override fun command(arg: JoinRequest): Account {
            val userId = arg.userId
            val email = arg.email
            if (isDuplicated(userId, email))
                throw DuplicateMemberException("UserId [$userId], email [$email] already exisits..")

            val account = Account(arg.userId, arg.credential, arg.name, arg.email, arg.phone)
            return accountRepository.save(account)
        }
    })

    private fun processJoin(joinRequest: JoinRequest, commander: Commander<Account, JoinRequest>): Account {
        return commander.command(joinRequest);
    }

    private fun isDuplicated(userId: String, email: String): Boolean =
        accountRepository.existByUserId(userId) && accountRepository.existByEmail(email)
}