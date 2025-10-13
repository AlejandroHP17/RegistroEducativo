package com.mx.liftechnology.registroeducativo.framework

import android.app.Application
import com.mx.liftechnology.core.network.networkModule
import com.mx.liftechnology.core.preference.preferenceModule
import com.mx.liftechnology.registroeducativo.di.assignmentModule
import com.mx.liftechnology.registroeducativo.di.calendarModule
import com.mx.liftechnology.registroeducativo.di.crudPartialModule
import com.mx.liftechnology.registroeducativo.di.crudStudentModule
import com.mx.liftechnology.registroeducativo.di.crudSubjectModule
import com.mx.liftechnology.registroeducativo.di.dispatcherModule
import com.mx.liftechnology.registroeducativo.di.forgetPasswordModule
import com.mx.liftechnology.registroeducativo.di.locationModule
import com.mx.liftechnology.registroeducativo.di.loginUserModule
import com.mx.liftechnology.registroeducativo.di.menuModule
import com.mx.liftechnology.registroeducativo.di.profileModule
import com.mx.liftechnology.registroeducativo.di.registerAssignmentModule
import com.mx.liftechnology.registroeducativo.di.registerSchoolModule
import com.mx.liftechnology.registroeducativo.di.registerUserModule
import com.mx.liftechnology.registroeducativo.di.sharedModule
import com.mx.liftechnology.registroeducativo.di.splashModule
import com.mx.liftechnology.registroeducativo.di.voiceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

/**
 * Custom [Application] class for the application.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class MyApp : Application() {

    /**
     * Called when the application is starting, before any other application objects have been created.
     */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initKoin()
    }

    /**
     * Initializes Koin for dependency injection.
     */
    private fun initKoin() {
        startKoin {
            androidContext(this@MyApp)
            modules(
                sharedModule,
                voiceModule,
                dispatcherModule,
                locationModule,
                networkModule,
                preferenceModule,
                splashModule,
                loginUserModule,
                registerUserModule,
                forgetPasswordModule,
                menuModule,
                registerSchoolModule,
                crudPartialModule,
                crudStudentModule,
                crudSubjectModule,
                profileModule,
                assignmentModule,
                registerAssignmentModule,
                calendarModule
            )
        }
    }
}