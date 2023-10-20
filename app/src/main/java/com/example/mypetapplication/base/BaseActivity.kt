package com.example.mypetapplication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        logNavigation("onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        logNavigation("onStart")
        super.onStart()
    }

    override fun onResume() {
        logNavigation("onResume")
        super.onResume()
    }

    override fun onPause() {
        logNavigation("onPause")
        super.onPause()
    }
}