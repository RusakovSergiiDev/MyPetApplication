package com.example.presentationmodule.data

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.LiveData

sealed class TopAppBarAction {

    class TextAction(
        @StringRes val textResId: Int,
        val callback: () -> Unit
    ) : TopAppBarAction()

    class IconVectorAction(
        val imageVector: ImageVector,
        val callback: () -> Unit
    ) : TopAppBarAction()

    class ToggleAction(
        val onCheckedChange: (Boolean) -> Unit,
        val isChecked: LiveData<Boolean>
    ) : TopAppBarAction()
}
