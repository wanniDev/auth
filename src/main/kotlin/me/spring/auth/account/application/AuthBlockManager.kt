package me.spring.auth.account.application

import me.spring.auth.account.domain.Id
import me.spring.auth.account.infrastructure.security.AuthInfo

interface AuthBlockManager<T> {
    fun add(id: T): Boolean
    fun existsById(id: Long): Boolean
}
