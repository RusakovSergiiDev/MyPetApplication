package com.example.mypetapplication.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * A custom implementation of LiveData for handling one-time events.
 */
class SingleLiveData<T> : MutableLiveData<T>() {

    private var hasHandledEvent = false

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) {
            if (hasHandledEvent) {
                // If the event has already been handled, do not trigger the observer again.
                return@observe
            }
            observer.onChanged(it)
            hasHandledEvent = true
        }
    }

    /**
     * Call this method to set a new value and trigger the observer.
     * This will allow the observer to receive the event even if it has already been handled.
     */
    override fun setValue(value: T?) {
        hasHandledEvent = false
        super.setValue(value)
    }

    /**
     * Call this method to post a new value and trigger the observer.
     * This will allow the observer to receive the event even if it has already been handled.
     */
    override fun postValue(value: T?) {
        hasHandledEvent = false
        super.postValue(value)
    }
}
