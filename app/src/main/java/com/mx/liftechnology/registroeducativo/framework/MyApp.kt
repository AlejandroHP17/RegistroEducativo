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
import com.mx.liftechnology.registroeducativo.di.controlModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

/**
 * Clase Application personalizada para la aplicación Registro Educativo.
 * 
 * Esta clase extiende [Application] y se encarga de la inicialización global de la aplicación.
 * 
 * **Responsabilidades:**
 * - Inicializa Timber para el logging de la aplicación
 * - Configura e inicializa Koin para la inyección de dependencias
 * - Registra todos los módulos de Koin necesarios para la aplicación
 *
 * **Módulos registrados:**
 * 
 * **Módulos de datos (DataCore):**
 * - [authDataCoreModule] - Repositorio y API de autenticación
 * - [evaluationDataCoreModule] - Repositorio y API de evaluaciones
 * - [formativeFieldDataCoreModule] - Repositorio y API de campos formativos
 * - [partialDataCoreModule] - Repositorio y API de parciales
 * - [schoolCycleDataCoreModule] - Repositorio y API de ciclos escolares
 * - [schoolDataCoreModule] - Repositorio y API de escuelas
 * - [studentDataCoreModule] - Repositorio y API de estudiantes
 * - [workTypeDataCoreModule] - Repositorio y API de tipos de trabajo
 * 
 * **Módulos de dominio y UI:**
 * - [authModule] - ViewModels y casos de uso de autenticación
 * - [evaluationModule] - ViewModels y casos de uso de evaluaciones
 * - [formativeFieldModule] - ViewModels y casos de uso de campos formativos
 * - [menuModule] - ViewModels y casos de uso del menú
 * - [partialModule] - ViewModels y casos de uso de parciales
 * - [schoolCycleModule] - ViewModels y casos de uso de ciclos escolares
 * - [shareDomainModule] - Casos de uso compartidos del dominio
 * - [studentModule] - ViewModels y casos de uso de estudiantes
 * - [workTypeModule] - ViewModels y casos de uso de tipos de trabajo
 * - [sharedModule] - ViewModel compartido para estado global
 * - [splashModule] - ViewModel de la pantalla de inicio
 * - [profileModule] - ViewModel del perfil de usuario
 * - [calendarModule] - ViewModel del calendario
 * - [controlModule] - ViewModel de control de APIs
 * 
 * **Módulos de utilidades:**
 * - [networkModule] - Configuración de Retrofit y servicios de red
 * - [preferenceModule] - Gestión de preferencias compartidas
 * - [deviceModule] - Utilidades del dispositivo
 * - [locationModule] - Servicios de ubicación
 * - [voiceModule] - Reconocimiento de voz
 * - [dispatcherModule] - Proveedores de corrutinas
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class MyApp : Application() {

    /**
     * Se llama cuando la aplicación está iniciando, antes de que se creen otros objetos de la aplicación.
     * 
     * Este método se ejecuta una sola vez durante el ciclo de vida de la aplicación,
     * cuando el proceso de la aplicación se inicia por primera vez.
     * 
     * **Flujo de inicialización:**
     * 1. Llama al método [onCreate] de la clase padre [Application]
     * 2. Inicializa Timber con [Timber.DebugTree] para el logging en modo debug
     * 3. Inicializa Koin con todos los módulos de dependencias necesarios
     * 
     * **Nota:** En producción, se recomienda usar un árbol de logging diferente
     * o condicionar la plantación del árbol de debug según el tipo de build.
     */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initKoin()
    }

    /**
     * Inicializa Koin para la inyección de dependencias.
     * 
     * Este método configura el contexto global de Koin y registra todos los módulos
     * necesarios para la aplicación. Los módulos se registran en el siguiente orden:
     * 
     * 1. **Módulos de datos (DataCore):** Repositorios e implementaciones de APIs
     * 2. **Módulos de dominio y UI:** ViewModels y casos de uso
     * 3. **Módulos de utilidades:** Servicios compartidos (red, preferencias, etc.)
     * 
     * **Importante:** El orden de registro puede ser relevante si hay dependencias
     * entre módulos. Los módulos de datos deben registrarse antes que los módulos
     * que los utilizan.
     * 
     * @see org.koin.core.context.GlobalContext.startKoin
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
                controlModule,
            )
        }
    }
}