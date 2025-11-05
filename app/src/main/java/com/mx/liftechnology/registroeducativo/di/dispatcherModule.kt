package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.util.DefaultDispatcherProvider
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Koin module for dispatcher-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val dispatcherModule = module {
    /**
     * Provides a singleton instance of [DispatcherProvider].
     */
    singleOf(::DefaultDispatcherProvider) {
        bind<DispatcherProvider>()
    }
}
