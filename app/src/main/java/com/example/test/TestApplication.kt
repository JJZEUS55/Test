package com.example.test

import android.app.Application
import com.facebook.stetho.Stetho

class TestApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}