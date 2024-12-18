package com.mx.liftechnology.core.preference

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val preferenceModule = module {

    // SharedPreferences
    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    // Repository
    single<PreferenceRepository> {
        PreferenceRepositoryImpl(get())
    }

    // Use Cases
    single {
        PreferenceUseCase(get())
    }
}
