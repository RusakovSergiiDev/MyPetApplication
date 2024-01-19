package com.example.mypetapplication.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.datamodule.types.Task
import com.example.logicmodule.usecases.IFlowTaskUseCase
import com.example.mypetapplication.utils.SimpleNavigationEvent
import com.example.mypetapplication.utils.SingleLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    // Internal param(s)
    private val isContentLoadingSourceFlow = MutableStateFlow(false)
    private val isContentInErrorStateSourceFlow = MutableStateFlow(false)

    // External param(s)
    val isLoadingLiveData: LiveData<Boolean> = isContentLoadingSourceFlow.asLiveData()
    val isContentInErrorStateLiveData: LiveData<Boolean> =
        isContentInErrorStateSourceFlow.asLiveData()

    // Event(s)
    val navigationBackEvent = SimpleNavigationEvent()
    val logOutEvent = SimpleNavigationEvent()
    val showErrorEvent = SingleLiveData<String>()

    private val customScope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    override fun onCleared() {
        super.onCleared()
        customScope.coroutineContext.cancel()
    }

    fun onBackClicked() {
        navigationBackEvent.call()
    }

    fun onLogOutClicked() {
        logOutEvent.call()
    }

    open fun onRetryClicked() {

    }

    fun <T> executeUseCase(
        useCase: IFlowTaskUseCase<T>,
        onSuccess: ((T) -> Unit)? = null,
        onEmpty: (() -> Unit)? = null,
    ) {
        handleLoading(true)
        customScope.launch {
            useCase.execute().flowOn(Dispatchers.IO).collect { task ->
                when (task) {
                    is Task.Success -> {
                        handleLoading(false)
                        onSuccess?.invoke(task.data)
                    }

                    is Task.Error -> {
                        handleLoading(false)
                        handleError(task.errorMessage)
                    }

                    is Task.Empty -> {
                        handleLoading(false)
                        onEmpty?.invoke()
                    }

                    is Task.Loading -> handleLoading(true)
                    else -> handleLoading(false)
                }
            }
        }
    }

    fun <T> executeForSuccessUseCase(
        useCase: IFlowTaskUseCase<T>,
        onSuccess: ((T) -> Unit)? = null
    ) {
        executeUseCase(useCase, onSuccess = onSuccess)
    }

    fun <T> executeAndWrapUseCase(
        useCase: IFlowTaskUseCase<T>
    ): Flow<Task<T>> {
        handleLoading(true)
        val flow: Flow<Task<T>> = channelFlow {
            customScope.launch {
                useCase.execute().collect { task ->
                    when (task) {
                        is Task.Success -> {
                            handleSuccess(task)
                        }

                        is Task.Empty -> {
                            handleLoading(false)
                        }

                        is Task.Error -> {
                            handleLoading(false)
                            handleError(task.errorMessage)
                        }

                        is Task.Loading -> handleLoading(true)
                        else -> handleLoading(false)
                    }
                    trySend(task)
                }
            }
        }
        return flow
    }

    fun <T> retryUseCase(useCase: IFlowTaskUseCase<T>) {
        customScope.launch {
            useCase.retry()
        }
    }

    private fun <T> handleSuccess(task: Task.Success<T>) {
        Log.d("myLogs", "handleSuccess")
        isContentLoadingSourceFlow.value = false
        isContentInErrorStateSourceFlow.value = false
    }

    private fun handleEmpty() {
        Log.d("myLogs", "handleEmpty")
        isContentLoadingSourceFlow.value = false
        isContentInErrorStateSourceFlow.value = false
    }

    private fun handleError(errorMessage: String) {
        Log.d("myLogs", "handleError:$errorMessage")
        showErrorEvent.postValue(errorMessage)
        isContentLoadingSourceFlow.value = false
        isContentInErrorStateSourceFlow.value = true
    }

    private fun handleLoading(isLoading: Boolean) {
        Log.d("myLogs", "handleLoading:$isLoading")
        isContentLoadingSourceFlow.value = true
        isContentInErrorStateSourceFlow.value = false
    }
}