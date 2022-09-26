package com.astro.test.rosyid

import android.app.Application
import android.content.Context

class MyApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun applicationContext(): Context = instance!!.applicationContext
    }
}