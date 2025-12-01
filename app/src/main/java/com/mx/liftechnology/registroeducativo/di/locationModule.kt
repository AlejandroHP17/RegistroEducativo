package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.util.location.LocationHelper
import org.koin.core.module.dsl.singleOf
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
    singleOf(::LocationHelper)
}
