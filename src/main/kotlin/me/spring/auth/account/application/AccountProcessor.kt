package me.spring.auth.account.application

import me.spring.auth.account.domain.Account
import me.spring.auth.account.presentation.request.AuthRequest
import me.spring.auth.account.presentation.request.AuthResponse
import me.spring.auth.account.presentation.request.JoinRequest
import me.spring.auth.exception.DuplicateAccountException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AccountProcessor(private val accountRepository: AccountRepositoryAdapter, private val authHelper: AuthHelper) {

    @Transactional
    fun processJoin(joinRequest: JoinRequest): Account = processJoin(joinRequest, confirmJoinWith(joinRequest))

    private fun confirmJoinWith(joinRequest: JoinRequest) = { arg: JoinRequest ->
        val userId = joinRequest.userId
            val email = arg.email
            if (isDuplicated(userId, email))
                throw DuplicateAccountException("UserId [$userId], email [$email] already exisits..")

            val account = Account(arg.userId, arg.credential, arg.name, arg.email, arg.phone)

            accountRepository.save(account)
        }
    private inline fun processJoin(joinRequest: JoinRequest, commander: (JoinRequest) -> Account): Account {
        return commander.invoke(joinRequest)
    }
    private fun isDuplicated(userId: String, email: String): Boolean =
        accountRepository.existByUserId(userId) && accountRepository.existByEmail(email)

    @Transactional
    fun processAuth(authRequest: AuthRequest): AuthResponse {
        val account = accountRepository.findByUserId(authRequest.userId)
        val authToken = authHelper.authenticate(account)
        return AuthResponse(authToken)
    }
}