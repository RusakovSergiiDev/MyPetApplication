package com.example.mypetapplication.base

data class FullScreenContent<T : IScreenContent>(
    val topAppBarContent: TopAppBarContent,
    val screenContent: T?,
)