package com.example.mypetapplication.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mypetapplication.R
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.base.ScreenId

class HomeFragment : BaseFragment() {

    override val screenId: ScreenId
        get() = ScreenId.Home

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}