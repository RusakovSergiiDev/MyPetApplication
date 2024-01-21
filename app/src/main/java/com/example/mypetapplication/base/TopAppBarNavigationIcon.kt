package com.example.mypetapplication.base

import androidx.compose.ui.graphics.vector.ImageVector

data class TopAppBarNavigationIcon(
    val imageVector: ImageVector,
    val callback: () -> Unit,
)
