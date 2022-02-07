package com.example.translateword.cicerone

import android.app.Application
import com.example.translateword.koin.application
import com.example.translateword.koin.historyScreen
import com.example.translateword.koin.mainScreen
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {


    companion object {
        lateinit var instance: App
    }

    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))
        }
    }
}