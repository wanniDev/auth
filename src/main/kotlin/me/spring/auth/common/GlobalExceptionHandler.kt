package me.spring.auth.common

import me.spring.auth.common.api.ApiResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@RequestMapping(produces = ["application/json"])
class GlobalExceptionHandler<T : Exception> {

    @ExceptionHandler(value = [Exception::class])
    fun handle(ex: T): ResponseEntity<*> {
        return ResponseEntity(ApiResult.ERROR(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST)
    }

}