package com.task.expencetracker

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
@HiltAndroidApp
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("AppApplication", "AppApplication created")
    }
}
