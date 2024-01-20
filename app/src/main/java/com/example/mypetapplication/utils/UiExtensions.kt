package com.example.mypetapplication.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

val undefined: Int = -1

fun Fragment.inflateView(layoutId: Int, container: ViewGroup?): View {
    val inflater = LayoutInflater.from(this.requireContext())
    return inflater.inflate(layoutId, container, false)
}