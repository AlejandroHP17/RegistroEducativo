package com.mx.liftechnology.registroeducativo.framework

import android.app.Application
import com.mx.liftechnology.registroeducativo.model.di.menuModule
import com.mx.liftechnology.registroeducativo.model.di.studentModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApp :  Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializar Koin aquí
        startKoin {
            androidLogger()  // Opcional: Log para depuración
            androidContext(this@MyApp)
            modules(menuModule, studentModule)
        }
    }
}