package com.example.androidtd

import android.app.Application
import com.example.androidtd.network.Api

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Api.INSTANCE = Api(this)
    }
}