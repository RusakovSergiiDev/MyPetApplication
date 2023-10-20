package com.example.mypetapplication.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mypetapplication.R
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.base.ScreenId
import com.example.mypetapplication.utils.inflateView

class SignUpFragment : BaseFragment() {

    override val screenId: ScreenId
        get() = ScreenId.AuthenticationSignUp

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflateView(R.layout.fragment_sign_up, container)
    }
}