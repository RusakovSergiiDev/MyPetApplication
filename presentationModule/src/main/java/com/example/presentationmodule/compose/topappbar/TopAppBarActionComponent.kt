package com.example.presentationmodule.compose.topappbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentationmodule.data.TopAppBarAction

@Composable
fun TopAppBarActionComponent(topAppBarAction: TopAppBarAction?) {
    topAppBarAction?.let { action ->
        when (action) {
            is TopAppBarAction.TextAction -> {
                Text(
                    modifier = Modifier
                        .clickable { action.callback.invoke() }
                        .padding(8.dp),
                    text = stringResource(id = action.textResId),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp
                )
            }

            is TopAppBarAction.IconVectorAction -> {
                IconButton(onClick = { action.callback.invoke() }) {
                    Icon(
                        imageVector = action.imageVector,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            is TopAppBarAction.ToggleAction -> {
                val checkedState = action.isChecked.observeAsState(initial = false)
                Switch(
                    modifier = Modifier
                        .padding(8.dp),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                        uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                    ),
                    onCheckedChange = { action.onCheckedChange(it) },
                    checked = checkedState.value
                )
            }

            is TopAppBarAction.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
