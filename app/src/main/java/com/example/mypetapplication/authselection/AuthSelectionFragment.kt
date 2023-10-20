package com.example.mypetapplication.authselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mypetapplication.R
import com.example.mypetapplication.utils.inflateView

class AuthSelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflateView(R.layout.fragment_auth_selection, container)
    }
}