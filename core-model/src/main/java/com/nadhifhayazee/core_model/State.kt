package com.nadhifhayazee.core_model

sealed class State<T> {
    class Loading<T> : State<T>()
    data class Success<T>(val data: T) : State<T>()
    data class Error<T>(val errorMessage: String?): State<T>()
}
