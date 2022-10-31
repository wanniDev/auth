package me.spring.auth.account.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
open class Account protected constructor() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    val userId: String? = null
    val password: String? = null
    val name: String? = null
    val email: String? = null
    val phone: String? = null
    val count: Int? = null
    val lastLoginTime: LocalDateTime? = null
    val createTime: LocalDateTime? = null
}