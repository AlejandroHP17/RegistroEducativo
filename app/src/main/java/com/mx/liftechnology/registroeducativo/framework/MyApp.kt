package com.mx.liftechnology.registroeducativo.framework

import android.app.Application
import com.mx.liftechnology.core.network.networkModule
import com.mx.liftechnology.core.preference.preferenceModule
import com.mx.liftechnology.registroeducativo.di.assignmentModule
import com.mx.liftechnology.registroeducativo.di.crudPartialModule
import com.mx.liftechnology.registroeducativo.di.crudStudentModule
import com.mx.liftechnology.registroeducativo.di.crudSubjectModule
import com.mx.liftechnology.registroeducativo.di.dispatcherModule
import com.mx.liftechnology.registroeducativo.di.forgetPasswordModule
import com.mx.liftechnology.registroeducativo.di.locationModule
import com.mx.liftechnology.registroeducativo.di.loginModule
import com.mx.liftechnology.registroeducativo.di.menuModule
import com.mx.liftechnology.registroeducativo.di.profileModule
import com.mx.liftechnology.registroeducativo.di.registerAssignmentModule
import com.mx.liftechnology.registroeducativo.di.registerModule
import com.mx.liftechnology.registroeducativo.di.registerSchoolModule
import com.mx.liftechnology.registroeducativo.di.splashModule
import com.mx.liftechnology.registroeducativo.di.voiceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

/** MyApp - Start the first and unics elements of the app, plus detect lifecycle from activities
 * @author pelkidev
 * @since 1.0.0
 * */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initKoin()
    }

    /** initKoin - Start the koin (dependency injection)
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initKoin() {
        startKoin {
            androidContext(this@MyApp)
            modules(
                voiceModule,
                dispatcherModule,
                locationModule,
                networkModule,
                preferenceModule,
                splashModule,
                loginModule,
                registerModule,
                forgetPasswordModule,
                menuModule,
                registerSchoolModule,
                crudPartialModule,
                crudStudentModule,
                crudSubjectModule,
                profileModule,
                assignmentModule,
                registerAssignmentModule
            )
        }
    }
}