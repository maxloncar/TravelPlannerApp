package com.example.travelplannerapp

import android.app.Application
import android.content.Context

class DestinationApp : Application() {

    companion object {
        lateinit var ApplicationContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        ApplicationContext = this
    }
}