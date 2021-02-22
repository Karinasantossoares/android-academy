package br.com.personal.karina.applealdesafio

import android.app.Application
import br.com.personal.karina.applealdesafio.di.modulesTraining
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(modulesTraining)
        }
    }
}