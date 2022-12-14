package me.spring.auth.account.domain

import me.spring.auth.account.infrastructure.jwt.JWT
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    var userId: String? = null
    var password: String? = null
    var name: String? = null
    var email: String? = null
    var phone: String? = null
    var count: Int? = 0
    var lastLoginTime: LocalDateTime? = null
    val createTime: LocalDateTime? = LocalDateTime.now()
    protected constructor()

    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }

    constructor(userId: String?, password: String?, name: String?, email: String?, phone: String?) {
        this.userId = userId
        this.password = password
        this.name = name
        this.email = email
        this.phone = phone
    }


    override fun toString(): String {
        return "Account(id=$id, userId=$userId, password=$password, name=$name, email=$email, phone=$phone, count=$count, lastLoginTime=$lastLoginTime, createTime=$createTime)"
    }

    fun newApiToken(jwt: JWT, authorities: Array<String>): String {
        return jwt.newToken(JWT.Claims.of(id, name, email, authorities))
    }

    fun encodePasswd(passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(password)
    }


}