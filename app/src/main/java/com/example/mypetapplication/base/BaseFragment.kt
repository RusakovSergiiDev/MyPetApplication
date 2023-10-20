package com.example.mypetapplication.base

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract val screenId: ScreenId?
}