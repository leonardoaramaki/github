package com.example.bliss

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class BlissApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}
