package com.mx.liftechnology.registroeducativo.framework

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.mx.liftechnology.core.network.networkModule
import com.mx.liftechnology.core.preference.preferenceModule
import com.mx.liftechnology.registroeducativo.di.crudStudentModule
import com.mx.liftechnology.registroeducativo.di.dispatcherModule
import com.mx.liftechnology.registroeducativo.di.forgetPasswordModule
import com.mx.liftechnology.registroeducativo.di.getListStudentModule
import com.mx.liftechnology.registroeducativo.di.listSubjectModule
import com.mx.liftechnology.registroeducativo.di.locationModule
import com.mx.liftechnology.registroeducativo.di.loginModule
import com.mx.liftechnology.registroeducativo.di.menuModule
import com.mx.liftechnology.registroeducativo.di.profileModule
import com.mx.liftechnology.registroeducativo.di.registerModule
import com.mx.liftechnology.registroeducativo.di.registerPartialModule
import com.mx.liftechnology.registroeducativo.di.registerSchoolModule
import com.mx.liftechnology.registroeducativo.di.registerSubjectModule
import com.mx.liftechnology.registroeducativo.di.splashModule
import com.mx.liftechnology.registroeducativo.di.subMenuModule
import com.mx.liftechnology.registroeducativo.di.voiceModule
import com.mx.liftechnology.registroeducativo.main.funextensions.log
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

/** MyApp - Start the first and unics elements of the app
 * @author pelkidev
 * @since 1.0.0
 * */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initKoin()
        initLifeCyclesActivity()
    }

    companion object {
        const val NAME_CYCLE = "LifeCyclePersonalize Activity: "
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
                subMenuModule,
                listSubjectModule,
                registerSchoolModule,
                registerPartialModule,
                crudStudentModule,
                registerSubjectModule,
                getListStudentModule,
                profileModule
            )
        }
    }

    private fun initLifeCyclesActivity() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.log("onActivityCreated", NAME_CYCLE)

                // Registrar fragment lifecycle en cada actividad
                if (activity is FragmentActivity) {
                    activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                        LifeCycle(), true
                    )
                }
            }

            override fun onActivityStarted(activity: Activity) {
                activity.log("onActivityStarted", NAME_CYCLE)
            }

            override fun onActivityResumed(activity: Activity) {
                activity.log("onActivityResumed", NAME_CYCLE)
            }

            override fun onActivityPaused(activity: Activity) {
                activity.log("onActivityPaused", NAME_CYCLE)
            }

            override fun onActivityStopped(activity: Activity) {
                activity.log("onActivityStopped", NAME_CYCLE)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                activity.log("onActivitySaveInstanceState", NAME_CYCLE)
            }

            override fun onActivityDestroyed(activity: Activity) {
                activity.log("onActivityDestroyed", NAME_CYCLE)
            }
        })
    }
}