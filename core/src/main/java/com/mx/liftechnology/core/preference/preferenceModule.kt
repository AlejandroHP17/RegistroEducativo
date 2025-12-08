/**
 * @file Contiene el módulo de Koin para las dependencias relacionadas con las preferencias.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.preference

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Módulo de Koin para las dependencias de SharedPreferences.
 * Este módulo se encarga de proveer las instancias de [SharedPreferences], [PreferenceRepository] y [PreferenceUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val preferenceModule = module {

    /**
     * Provee una instancia singleton de [SharedPreferences].
     */
    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    /**
     * Provee una instancia singleton de [PreferenceRepository].
     */
    singleOf(::PreferenceRepositoryImpl){
        bind<PreferenceRepository>()
    }

    /**
     * Provee una instancia singleton de [PreferenceUseCase].
     */
    singleOf(::PreferenceUseCase)
}
