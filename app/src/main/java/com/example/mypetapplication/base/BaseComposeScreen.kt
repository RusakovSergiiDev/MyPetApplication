package com.example.mypetapplication.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.example.presentationmodule.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : IBaseScreenContent> BaseComposeScreen(
    onBackClicked: (() -> Unit)? = null,
    onRetryClicked: (() -> Unit)? = null,
    isShowLoading: LiveData<Boolean>,
    isShowRetry: LiveData<Boolean>,
    contentLiveData: LiveData<BaseFullComposeScreenContent<T>>,
    content: @Composable (LiveData<BaseFullComposeScreenContent<T>>) -> Unit
) {
    val contentState = contentLiveData.observeAsState()
    AppTheme {
        Scaffold(
            topBar = {
                val titleResId = contentState.value?.topAppBarTitleResId
                val title = titleResId?.let { stringResource(id = titleResId) } ?: ""

                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    title = {
                        titleResId ?: return@TopAppBar
                        Text(
                            text = title,
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
                    // actions = actions ?: {},
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    content(contentLiveData)
                    val isLoadingState = isShowLoading.observeAsState().value ?: false
                    val isRetryState = isShowRetry.observeAsState().value ?: false
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
                            Text("Retry")
                        }
                    }
                }
            }
        )
    }
}