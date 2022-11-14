package me.spring.auth.account.domain

import javax.persistence.*
import javax.persistence.Id

@Entity
class AccountToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToOne(fetch = FetchType.LAZY)
    var account:Account? = null

    var token: String? = null

    protected constructor()

    constructor(account: Account, token: String) {
        this.account = account
        this.token = token
    }
}