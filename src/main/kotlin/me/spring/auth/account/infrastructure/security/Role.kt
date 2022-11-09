package me.spring.auth.account.infrastructure.security

enum class Role(val value: String) {
    USER(value = "ROLE_USER"),
    ADMIN(value = "ROLE_ADMIN")
}