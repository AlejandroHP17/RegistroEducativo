package com.mx.liftechnology.domain.module

import android.content.Context
import android.content.SharedPreferences
import com.mx.liftechnology.core.preference.PreferenceRepository
import com.mx.liftechnology.core.preference.PreferenceRepositoryImpl
import com.mx.liftechnology.core.preference.PreferenceUseCase
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
