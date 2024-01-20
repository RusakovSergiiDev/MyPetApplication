package com.example.datamodule.types

sealed class Task<out T> {
    object Initial : Task<Nothing>()
    object Loading : Task<Nothing>()
    data class Success<T>(val data: T) : Task<T>()
    object Empty : Task<Nothing>()
    data class Error(val errorMessage: String) : Task<Nothing>()
}

fun <T> Task<T>.isInitial(): Boolean {
    return this is Task.Initial
}

fun <T> Task<T>.isLoading(): Boolean {
    return this is Task.Loading
}

fun <T> Task<T>.isSuccess(): Boolean {
    return this is Task.Success
}

fun <T> Task<T>.isEmpty(): Boolean {
    return this is Task.Empty
}

fun <T> Task<T>.isError(): Boolean {
    return this is Task.Error
}

fun <T> Task<T>.isSuccessOrEmpty(): Boolean {
    return isSuccess() || isEmpty()
}

fun <T> Task<T>.getLogName(): String {
    return when (this) {
        is Task.Initial -> "Initial"
        is Task.Loading -> "Loading"
        is Task.Success -> "Success"
        is Task.Empty -> "Empty"
        is Task.Error -> "Error"
    }
}