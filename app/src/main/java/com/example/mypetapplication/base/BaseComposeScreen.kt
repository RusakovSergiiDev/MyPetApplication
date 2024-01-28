package com.example.mypetapplication.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.example.mypetapplication.utils.log
import com.example.presentationmodule.R
import com.example.presentationmodule.AppTheme
import com.example.presentationmodule.compose.topappbar.TopAppBarActionComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : IScreenContent> BaseComposeScreen(
    isShowGlobalSnackbarError: State<String?>,
    isShowGlobalLoading: State<Boolean>?,
    isShowGlobalRetry: State<Boolean>,
    onRetryClicked: (() -> Unit)? = null,
    topAppBarContentLiveData: LiveData<TopAppBarContent>,
    screenContentLiveData: LiveData<T?>,
    contentScreen: @Composable (State<T?>) -> Unit
) {
    val topAppBarContentState = topAppBarContentLiveData.observeAsState()
    val screenContentState = screenContentLiveData.observeAsState()
    AppTheme {
        val topAppBarContent = topAppBarContentState.value
        Scaffold(
            topBar = {
                topAppBarContent ?: return@Scaffold
                if (!topAppBarContent.isShow) return@Scaffold
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    title = {
                        val titleResId = topAppBarContent.titleResId
                        titleResId ?: return@TopAppBar
                        Text(
                            text = stringResource(id = titleResId),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        val navigationIcon = topAppBarContent.navigationIcon
                        navigationIcon ?: return@TopAppBar
                        IconButton(onClick = { navigationIcon.callback.invoke() }) {
                            Icon(
                                imageVector = navigationIcon.imageVector,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    actions = {
                        val actions = topAppBarContent.actions
                        actions.forEach {
                            TopAppBarActionComponent(topAppBarAction = it)
                        }
                    },
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    contentScreen(screenContentState)

                    val snackbarError = isShowGlobalSnackbarError.value
                    val isSnackBarErrorVisible = !snackbarError.isNullOrBlank()
                    AnimatedVisibility(
                        visible = isSnackBarErrorVisible,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.error)
                                .padding(16.dp),
                            text = snackbarError ?: "",
                            color = MaterialTheme.colorScheme.onError
                        )
                    }

                    val isLoadingState = isShowGlobalLoading?.value ?: false
                    val isRetryState = isShowGlobalRetry.value
                    if (isLoadingState) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center)
                        )
                    } else if (isRetryState) {
                        Button(
                            onClick = { onRetryClicked?.invoke() },
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text(stringResource(id = R.string.label_retry))
                        }
                    }
                }
            }
        )
    }
}
