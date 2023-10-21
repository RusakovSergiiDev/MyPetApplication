package com.example.mypetapplication.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<VM : BaseViewModel>(private val viewModelJavaClass: Class<VM>) :
    Fragment() {

    abstract val screenId: ScreenId?

    abstract fun onSetupObservers()

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[viewModelJavaClass]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSetupObservers()
    }
}