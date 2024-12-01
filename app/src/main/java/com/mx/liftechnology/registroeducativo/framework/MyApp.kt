package com.mx.liftechnology.registroeducativo.framework

import android.app.Application
import com.mx.liftechnology.core.network.networkModule
import com.mx.liftechnology.domain.module.preferenceModule
import com.mx.liftechnology.registroeducativo.di.dispatcherModule
import com.mx.liftechnology.registroeducativo.di.forgetPasswordModule
import com.mx.liftechnology.registroeducativo.di.locationModule
import com.mx.liftechnology.registroeducativo.di.loginModule
import com.mx.liftechnology.registroeducativo.di.menuModule
import com.mx.liftechnology.registroeducativo.di.registerModule
import com.mx.liftechnology.registroeducativo.di.registerPartialModule
import com.mx.liftechnology.registroeducativo.di.registerSchoolModule
import com.mx.liftechnology.registroeducativo.di.splashModule
import com.mx.liftechnology.registroeducativo.di.subMenuModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

/** MyApp - Start the first and unics elements of the app
 * @author pelkidev
 * @since 1.0.0
 * */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    /** initKoin - Start the koin (dependency injection)
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initKoin() {
        startKoin {
            androidLogger()  // Opcional: Log para depuración
            androidContext(this@MyApp)
            modules(
                dispatcherModule,
                locationModule,
                networkModule,
                preferenceModule,
                splashModule,
                loginModule,
                registerModule,
                forgetPasswordModule,
                menuModule,
                subMenuModule,
                registerSchoolModule,
                registerPartialModule
            )
        }
    }
}