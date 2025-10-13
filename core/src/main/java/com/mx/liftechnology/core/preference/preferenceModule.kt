package com.mx.liftechnology.core.preference

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin module for SharedPreferences and related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val preferenceModule = module {

    /**
     * Provides a singleton instance of [SharedPreferences].
     */
    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    /**
     * Provides a singleton instance of [PreferenceRepository].
     */
    single<PreferenceRepository> {
        PreferenceRepositoryImpl(get())
    }

    /**
     * Provides a singleton instance of [PreferenceUseCase].
     */
    single {
        PreferenceUseCase(get())
    }
}
