package com.mx.liftechnology.registroeducativo.framework

import android.app.Application
import com.mx.liftechnology.core.network.util.networkModule
import com.mx.liftechnology.core.preference.preferenceModule
import com.mx.liftechnology.core.util.device.deviceModule
import com.mx.liftechnology.registroeducativo.di.authDataCoreModule
import com.mx.liftechnology.registroeducativo.di.calendarModule
import com.mx.liftechnology.registroeducativo.di.crudFormativeFieldModule
import com.mx.liftechnology.registroeducativo.di.crudPartialModule
import com.mx.liftechnology.registroeducativo.di.crudStudentModule
import com.mx.liftechnology.registroeducativo.di.dispatcherModule
import com.mx.liftechnology.registroeducativo.di.evaluationDataCoreModule
import com.mx.liftechnology.registroeducativo.di.forgetPasswordModule
import com.mx.liftechnology.registroeducativo.di.formativeFieldDataCoreModule
import com.mx.liftechnology.registroeducativo.di.locationModule
import com.mx.liftechnology.registroeducativo.di.loginUserModule
import com.mx.liftechnology.registroeducativo.di.menuModule
import com.mx.liftechnology.registroeducativo.di.partialDataCoreModule
import com.mx.liftechnology.registroeducativo.di.profileModule
import com.mx.liftechnology.registroeducativo.di.registerEvaluationModule
import com.mx.liftechnology.registroeducativo.di.registerSchoolModule
import com.mx.liftechnology.registroeducativo.di.registerUserModule
import com.mx.liftechnology.registroeducativo.di.schoolCycleDataCoreModule
import com.mx.liftechnology.registroeducativo.di.schoolDataCoreModule
import com.mx.liftechnology.registroeducativo.di.sharedModule
import com.mx.liftechnology.registroeducativo.di.splashModule
import com.mx.liftechnology.registroeducativo.di.studentDataCoreModule
import com.mx.liftechnology.registroeducativo.di.validationModule
import com.mx.liftechnology.registroeducativo.di.voiceModule
import com.mx.liftechnology.registroeducativo.di.workTypeDataCoreModule
import com.mx.liftechnology.registroeducativo.di.workTypeModule
import com.mx.liftechnology.registroeducativo.di.wotyFofiModule
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
                authDataCoreModule,
                evaluationDataCoreModule,
                formativeFieldDataCoreModule,
                partialDataCoreModule,
                schoolCycleDataCoreModule,
                schoolDataCoreModule,
                studentDataCoreModule,
                workTypeDataCoreModule,


                sharedModule,
                voiceModule,
                dispatcherModule,
                locationModule,
                networkModule,
                preferenceModule,
                deviceModule,
                splashModule,
                loginUserModule,
                registerUserModule,
                forgetPasswordModule,
                menuModule,
                registerSchoolModule,
                crudPartialModule,
                crudStudentModule,
                crudFormativeFieldModule,
                profileModule,
                wotyFofiModule,
                registerEvaluationModule,
                calendarModule,
                validationModule,
                workTypeModule
            )
        }
    }
}