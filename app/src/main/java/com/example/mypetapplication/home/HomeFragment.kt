package com.example.mypetapplication.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.map
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.base.ScreenId
import com.example.mypetapplication.home.compose.HomeScreen
import com.example.mypetapplication.ui.map.map

class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.Home

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(this.requireContext()).apply {
        setContent {
            HomeScreen(
                homeMainOptionUiItemsState = viewModel.homeMainOptionsLiveData.map { items ->
                    items.map(
                        requireContext()
                    )
                }.observeAsState(initial = emptyList())
            )
        }
    }

    override fun onSetupObservers() {

    }
}