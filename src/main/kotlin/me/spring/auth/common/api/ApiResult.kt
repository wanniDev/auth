package me.spring.auth.common.api

import org.springframework.http.HttpStatus

class ApiResult<T>(val isSuccess: Boolean, val response: T, val error: ApiError?) {

    companion object {
        fun <T> OK(response: T): ApiResult<T> {
            return ApiResult(true, response, null)
        }

        fun ERROR(throwable: Throwable, status: HttpStatus): ApiResult<*> {
            return ApiResult<Any?>(false, null, ApiError(throwable, status))
        }
    }

    override fun toString(): String {
        return "ApiResult{" +
                "success=" + isSuccess +
                ", response=" + response +
                ", error=" + error +
                '}'
    }
}