package me.spring.auth.account.infrastructure.security

enum class Role(val value: String) {
    USER(value = "USER"),
    ADMIN(value = "ADMIN")
}