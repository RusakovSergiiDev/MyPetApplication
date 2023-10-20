package com.example.mypetapplication.base

import android.content.Context
import android.util.Log

const val NAVIGATION_LOG_TAG = "NAVIGATION_LOG_TAG"

fun Context.logNavigation(msg: String) {
    Log.d(NAVIGATION_LOG_TAG, msg)
}