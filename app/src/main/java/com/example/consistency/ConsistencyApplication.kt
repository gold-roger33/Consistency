package com.example.consistency

import android.app.Application
import com.example.consistency.data.AppContainer
import com.example.consistency.data.AppDataContainer

class ConsistencyApplication : Application() {
lateinit var  container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}