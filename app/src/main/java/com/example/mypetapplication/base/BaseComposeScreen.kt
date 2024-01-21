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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.lifecycle.switchMap
import com.example.mypetapplication.utils.undefined
import com.example.presentationmodule.R
import com.example.presentationmodule.AppTheme
import com.example.presentationmodule.compose.topappbar.TopAppBarActionComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : IBaseScreenContent> BaseComposeScreen(
    onBackClicked: (() -> Unit)? = null,
    isShowSnackbarError: State<String?>,
    isShowLoading: State<Boolean>,
    onRetryClicked: (() -> Unit)? = null,
    isShowRetry: State<Boolean>,
    fullScreenContentLiveData: LiveData<BaseFullComposeScreenContent<T>>,
    contentScreen: @Composable (State<T?>) -> Unit
) {
    val fullScreenContentState = fullScreenContentLiveData.observeAsState()
    val screenContentState = fullScreenContentLiveData.switchMap { it.content }.observeAsState()
    AppTheme {
        Scaffold(
            topBar = {
                val titleResId = fullScreenContentState.value?.topAppBarTitleResId
                if (titleResId == undefined) {
                    return@Scaffold
                }
                val titleText = titleResId?.let { stringResource(id = titleResId) }
                val action =
                    fullScreenContentState.value?.topAppBarAction?.observeAsState()?.value

                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    title = {
                        titleText ?: return@TopAppBar
                        Text(
                            text = titleText,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        onBackClicked ?: return@TopAppBar
                        IconButton(onClick = { onBackClicked.invoke() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    actions = {
                        action?.let {
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

                    val snackbarError = isShowSnackbarError.value
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

                    val isLoadingState = isShowLoading.value
                    val isRetryState = isShowRetry.value
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
