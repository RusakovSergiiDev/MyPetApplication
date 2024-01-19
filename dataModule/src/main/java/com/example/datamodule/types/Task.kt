package com.example.datamodule.types

sealed class Task<out T> {
    object Initial : Task<Nothing>()
    object Loading : Task<Nothing>()
    data class Success<T>(val data: T) : Task<T>()
    object Empty : Task<Nothing>()
    data class Error(val errorMessage: String) : Task<Nothing>()
}

fun <T> Task<T>.isSuccess(): Boolean {
    return this is Task.Success
}

fun <T> Task<T>.isInitial(): Boolean {
    return this is Task.Initial
}