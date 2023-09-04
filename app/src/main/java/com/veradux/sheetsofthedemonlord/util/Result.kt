package com.veradux.sheetsofthedemonlord.util

sealed class ResultState<T> {
    class Loading<T> : ResultState<T>()
    class Empty<T> : ResultState<T>()
    class Failed<T>(val exception: Exception) : ResultState<T>()
    class Received<T>(val result: T) : ResultState<T>()
}
