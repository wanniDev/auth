package me.spring.auth.common

interface Commander<T, A> {
    fun command(arg:A): T
}