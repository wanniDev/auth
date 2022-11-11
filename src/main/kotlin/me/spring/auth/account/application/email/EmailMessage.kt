package me.spring.auth.account.application.email

data class EmailMessage(val to: String, val from: String, val message: String)