package me.spring.auth.common

import me.spring.auth.common.api.ApiResult
import me.spring.auth.exception.DuplicateMemberException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@RequestMapping(produces = ["application/json"])
class AccountExceptionHandler<T : Exception> {

    @ExceptionHandler(value = [DuplicateMemberException::class])
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    fun handle(ex: T): ApiResult<*> {
        return ApiResult.ERROR(ex, HttpStatus.UNAUTHORIZED)
    }
}