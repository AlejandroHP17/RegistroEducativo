/**
 * @file Define la clase Application principal de la aplicación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.registroeducativo.framework

import android.app.Application
import com.mx.liftechnology.core.network.util.networkModule
import com.mx.liftechnology.core.preference.preferenceModule
import com.mx.liftechnology.core.util.device.deviceModule
import com.mx.liftechnology.registroeducativo.di.authModule
import com.mx.liftechnology.registroeducativo.di.dataCore.authDataCoreModule
import com.mx.liftechnology.registroeducativo.di.calendarModule
import com.mx.liftechnology.registroeducativo.di.formativeFieldModule
import com.mx.liftechnology.registroeducativo.di.studentModule
import com.mx.liftechnology.registroeducativo.di.dispatcherModule
import com.mx.liftechnology.registroeducativo.di.dataCore.evaluationDataCoreModule
import com.mx.liftechnology.registroeducativo.di.dataCore.formativeFieldDataCoreModule
import com.mx.liftechnology.registroeducativo.di.locationModule
import com.mx.liftechnology.registroeducativo.di.dataCore.partialDataCoreModule
import com.mx.liftechnology.registroeducativo.di.profileModule
import com.mx.liftechnology.registroeducativo.di.dataCore.schoolCycleDataCoreModule
import com.mx.liftechnology.registroeducativo.di.dataCore.schoolDataCoreModule
import com.mx.liftechnology.registroeducativo.di.sharedModule
import com.mx.liftechnology.registroeducativo.di.splashModule
import com.mx.liftechnology.registroeducativo.di.dataCore.studentDataCoreModule
import com.mx.liftechnology.registroeducativo.di.voiceModule
import com.mx.liftechnology.registroeducativo.di.dataCore.workTypeDataCoreModule
import com.mx.liftechnology.registroeducativo.di.evaluationModule
import com.mx.liftechnology.registroeducativo.di.menuModule
import com.mx.liftechnology.registroeducativo.di.partialModule
import com.mx.liftechnology.registroeducativo.di.schoolCycleModule
import com.mx.liftechnology.registroeducativo.di.shareDomainModule
import com.mx.liftechnology.registroeducativo.di.workTypeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

/**
 * Clase Application personalizada para la aplicación.
 * 
 * **Responsabilidades:**
 * - Inicializa Timber para el logging
 * - Configura e inicializa Koin para la inyección de dependencias
 * - Registra todos los módulos de Koin necesarios para la aplicación
 *
 * **Módulos registrados:**
 * - Módulos de datos (auth, evaluation, formativeField, partial, schoolCycle, school, student, workType)
 * - Módulos de UI (splash, login, register, menu, profile, etc.)
 * - Módulos de utilidades (network, preference, device, location, voice, dispatcher)
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class MyApp : Application() {

    /**
     * Se llama cuando la aplicación está iniciando, antes de que se creen otros objetos de la aplicación.
     * 
     * **Flujo de inicialización:**
     * 1. Inicializa Timber para el logging
     * 2. Inicializa Koin con todos los módulos de dependencias
     */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initKoin()
    }

    /**
     * Inicializa Koin para la inyección de dependencias.
     * 
     * Registra todos los módulos necesarios para la aplicación, incluyendo:
     * - Módulos de datos (repositorios, APIs)
     * - Módulos de casos de uso
     * - Módulos de ViewModels
     * - Módulos de utilidades (red, preferencias, dispositivos, etc.)
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

                authModule,
                evaluationModule,
                formativeFieldModule,
                menuModule,
                partialModule,
                schoolCycleModule,
                shareDomainModule,
                studentModule,
                workTypeModule,

                sharedModule,
                voiceModule,
                dispatcherModule,
                locationModule,
                networkModule,
                preferenceModule,
                deviceModule,
                splashModule,
                profileModule,
                calendarModule,
            )
        }
    }
}