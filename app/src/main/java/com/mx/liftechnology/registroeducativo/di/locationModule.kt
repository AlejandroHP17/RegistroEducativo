package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.util.LocationHelper
import org.koin.dsl.module

/**
 * Koin module for location-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val locationModule = module {
    /**
     * Provides a singleton instance of [LocationHelper].
     */
    single { LocationHelper(get()) }
}
