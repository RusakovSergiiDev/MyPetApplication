package com.example.mypetapplication.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.mypetapplication.ui.data.HomeMainOptionUIiItem

fun Fragment.inflateView(layoutId: Int, container: ViewGroup?): View {
    val inflater = LayoutInflater.from(this.requireContext())
    return inflater.inflate(layoutId, container, false)
}