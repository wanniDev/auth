package me.spring.auth.account.application

interface EnrollUserManager {
    fun enroll(token: String): Boolean
    fun isPending(token: String): Boolean
}