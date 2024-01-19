package com.example.mypetapplication.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.datamodule.types.Task
import com.example.logicmodule.usecases.IUseCase
import com.example.mypetapplication.utils.SimpleNavigationEvent
import com.example.mypetapplication.utils.SingleLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    // Internal param(s)
    private val isContentLoadingSourceFlow = MutableStateFlow(false)

    // External param(s)
    val isLoadingLiveData: LiveData<Boolean> = isContentLoadingSourceFlow.asLiveData()

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
        useCase: IUseCase<T>,
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

    fun <T> retryUseCase(useCase: IUseCase<T>) {
        customScope.launch {
            useCase.retry()
        }
    }

    private fun handleError(errorMessage: String) {
        showErrorEvent.postValue(errorMessage)
    }

    private fun handleLoading(isLoading: Boolean) {
        isContentLoadingSourceFlow.value = isLoading
    }
}