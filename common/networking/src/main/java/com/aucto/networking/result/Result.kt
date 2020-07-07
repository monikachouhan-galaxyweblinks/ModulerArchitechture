package com.aucto.networking.result

sealed class Result<T, E : Exception> {
    data class Success<T, E : Exception>(val data: T) : Result<T, E>()
    data class Failure<T, E : Exception>(val exception: E) : Result<T, E>()

    fun <R : Exception> mapError(transform: (E) -> R): Result<T, R> = when (this) {
        is Success -> Success(data)
        is Failure -> Failure(transform(exception))
    }
}