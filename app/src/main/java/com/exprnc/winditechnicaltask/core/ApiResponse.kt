package com.exprnc.winditechnicaltask.core

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val error: ValidationError) : ApiResponse<T>()

    fun apiErrorHandle() : T {
        return when(this) {
            is Success -> data
            is Error -> throw error
        }
    }
}

data class ValidationError(val detail: List<Detail>) : RuntimeException()

data class Detail(
    val loc: List<String>,
    val msg: String,
    val type: String
)