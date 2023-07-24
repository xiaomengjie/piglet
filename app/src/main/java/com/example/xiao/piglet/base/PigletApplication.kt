package com.example.xiao.piglet.base

import android.app.Application
import android.content.Context

class PigletApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object{
        lateinit var context: Context
    }
}