package me.spring.auth.common.api

import org.springframework.http.HttpStatus

class ApiError internal constructor(message: String?, status: HttpStatus) {
    val status: Int
    var message: String = ""

    internal constructor(throwable: Throwable, status: HttpStatus) : this(throwable.message, status)

    init {
        this.status = status.value()
        if (message != null) {
            this.message = message
        }
    }
}