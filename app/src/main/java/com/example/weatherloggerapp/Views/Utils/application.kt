package com.example.weatherloggerapp.Views.Utils

import android.app.Application
import android.content.Context

class application : Application() {

    companion object {
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
    }

}

