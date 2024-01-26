package com.example.logicmodule.repository

import android.util.Log
import com.example.datamodule.types.ScreenId
import javax.inject.Inject

class AnalyticsRepository @Inject constructor() {

    companion object {
        private const val ANALYTICS_LOG_TAG = "APP_ANALYTICS"
    }

    fun screenOpened(screenId: ScreenId) {
        Log.d(ANALYTICS_LOG_TAG, screenId.name)
    }
}