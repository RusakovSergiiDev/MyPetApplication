package com.example.mypetapplication.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun <T> State<List<T>>.toStateList(): List<State<T>> {
    return this.value.map { item ->
        remember { mutableStateOf(item) }
    }
}