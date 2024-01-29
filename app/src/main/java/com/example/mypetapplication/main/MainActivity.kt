package com.example.mypetapplication.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.mypetapplication.MyPetApplicationService
import com.example.mypetapplication.R
import com.example.mypetapplication.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        findViewById<View>(R.id.start).setOnClickListener {
            startService(Intent(this, MyPetApplicationService::class.java))
        }
        findViewById<View>(R.id.end).setOnClickListener {
            stopService(Intent(this, MyPetApplicationService::class.java))
        }
    }
}