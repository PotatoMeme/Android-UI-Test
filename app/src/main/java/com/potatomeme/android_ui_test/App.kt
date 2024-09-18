package com.potatomeme.android_ui_test

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        // Firebase 수동 초기화
        FirebaseApp.initializeApp(this)
    }
}