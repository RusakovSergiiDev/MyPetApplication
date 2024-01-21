package com.example.mypetapplication.base

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.datamodule.types.Task
import com.example.datamodule.types.isError
import com.example.logicmodule.usecases.IFlowTaskUseCase
import com.example.mypetapplication.utils.SimpleNavigationEvent
import com.example.mypetapplication.utils.SingleLiveData
import com.example.presentationmodule.data.TopAppBarAction
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    companion object {
        private const val LOG_TAG = "BASE_VIEW_MODEL"
    }

    // Internal param(s)
    private val isContentLoadingFlowSource = MutableStateFlow(false)
    private val isContentInErrorStateFlowSource = MutableStateFlow(false)
    private val customScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var customScopeJob: Job? = null
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(LOG_TAG, "errorHandler -> $exception")
        Log.d(LOG_TAG, "errorHandler -> ${customScope.isActive}")
    }
    abstract val topAppBarContentLiveData: LiveData<TopAppBarContent>

    // External param(s)
    val isLoadingLiveData: LiveData<Boolean> = isContentLoadingFlowSource.asLiveData()
    val isContentInErrorStateLiveData: LiveData<Boolean> =
        isContentInErrorStateFlowSource.asLiveData()

    // Event(s)
    val snackbarErrorEvent = SingleLiveData<String?>()
    val navigationBackEvent = SimpleNavigationEvent()
    val logOutEvent = SimpleNavigationEvent()
    val showErrorEvent = SingleLiveData<String>()

    override fun onCleared() {
        super.onCleared()
        customScope.cancel()
    }

    fun onBackClicked() {
        navigationBackEvent.call()
    }

    open fun onLogOutClicked() {
        logOutEvent.call()
    }

    open fun onRetryClicked() {

    }

    private fun <T> executeUseCase(
        useCase: IFlowTaskUseCase<T>,
        onTask: ((Task<T>) -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null,
        onEmpty: (() -> Unit)? = null,
    ) {
        handleLoading()
        customScope.launch(errorHandler) {
            useCase.execute().collect { task ->
                withContext(Dispatchers.Main) {
                    onTask?.invoke(task)
                    when (task) {
                        is Task.Success -> handleSuccess(task, onSuccess)
                        is Task.Error -> handleError(task)
                        is Task.Empty -> handleEmpty(onEmpty)
                        is Task.Loading -> handleLoading()
                        else -> handleFinish()
                    }
                }
            }
        }
    }

    protected fun <T> executeForTaskResultUseCase(
        useCase: IFlowTaskUseCase<T>,
        onTask: ((Task<T>) -> Unit)? = null
    ) {
        executeUseCase(useCase = useCase, onTask = onTask)
    }

    protected fun <T> executeForSuccessTaskResultUseCase(
        useCase: IFlowTaskUseCase<T>,
        onSuccess: ((T) -> Unit)? = null
    ) {
        executeUseCase(useCase = useCase, onSuccess = onSuccess)
    }

    protected fun <T> executeAndWrapUseCase(
        useCase: IFlowTaskUseCase<T>
    ): Flow<Task<T>> {
        handleLoading()
        val flow: Flow<Task<T>> = channelFlow {
            customScopeJob = customScope.launch {
                useCase.execute().collect { task ->
                    when (task) {
                        is Task.Success -> handleSuccess(task = task)
                        is Task.Empty -> handleEmpty()
                        is Task.Error -> handleError(task = task)
                        is Task.Loading -> handleLoading()
                        else -> handleFinish()
                    }
                    trySend(task)
                }
            }
        }
        return flow
    }

    fun <T> retryUseCase(useCase: IFlowTaskUseCase<T>) {
        Log.d(LOG_TAG, "retryUseCase")
        customScope.launch {
            useCase.retry()
        }
    }

    private fun <T> handleSuccess(
        task: Task.Success<T>,
        onSuccess: ((T) -> Unit)? = null,
    ) {
        Log.d(LOG_TAG, "handleSuccess")
        isContentLoadingFlowSource.value = false
        isContentInErrorStateFlowSource.value = false
        onSuccess?.invoke(task.data)
    }

    private fun handleEmpty(
        onEmpty: (() -> Unit)? = null
    ) {
        Log.d(LOG_TAG, "handleEmpty")
        isContentLoadingFlowSource.value = false
        isContentInErrorStateFlowSource.value = false
        onEmpty?.invoke()
    }

    private fun handleError(task: Task.Error) {
        val errorMessage = task.errorMessage
        Log.d(LOG_TAG, "handleError:${errorMessage}")
        showErrorEvent.value = errorMessage
        isContentLoadingFlowSource.value = false
        isContentInErrorStateFlowSource.value = true
    }

    fun handleLoading() {
        Log.d(LOG_TAG, "handleLoading")
        isContentLoadingFlowSource.value = true
        isContentInErrorStateFlowSource.value = false
    }

    private fun handleFinish() {
        Log.d(LOG_TAG, "handleFinish")
        isContentLoadingFlowSource.value = false
        isContentInErrorStateFlowSource.value = false
    }

    protected fun <T> Task<T>.createRetryTopAppBarAction(
        callback: () -> Unit,
    ): TopAppBarAction? {
        val topAppBarAction = if (this.isError()) {
            TopAppBarAction.IconVectorAction(
                imageVector = Icons.Default.Refresh,
                callback = { callback.invoke() }
            )
        } else {
            null
        }
        return topAppBarAction
    }
}