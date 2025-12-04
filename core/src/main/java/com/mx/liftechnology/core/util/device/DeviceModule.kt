/**
 * @file Contiene el módulo de Koin para utilidades del dispositivo.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.util.device

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Módulo de Koin para utilidades del dispositivo.
 * Este módulo se encarga de proveer las instancias de utilidades relacionadas con el dispositivo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val deviceModule = module {

    /**
     * Provee una instancia singleton de [DeviceIdHelper] para obtener identificadores únicos del dispositivo.
     * Utiliza ANDROID_ID de forma segura para identificar dispositivos de manera única.
     */
    single {
        DeviceIdHelper(androidContext())
    }
}



