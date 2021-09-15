package com.example.dadjokes.util

import android.app.Application
import android.content.Context


class BaseApplication: Application() {

    init { instance = this }
    companion object {
          lateinit var instance: BaseApplication
        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }
}