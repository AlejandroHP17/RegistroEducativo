package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.util.location.LocationHelper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Módulo de Koin para dependencias relacionadas con la ubicación.
 * 
 * Este módulo proporciona las instancias necesarias para:
 * - Gestión de servicios de ubicación del dispositivo
 * - Obtención de coordenadas GPS
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val locationModule = module {
    /**
     * Proporciona una instancia singleton de [LocationHelper].
     * Helper para gestionar los servicios de ubicación del dispositivo.
     */
    singleOf(::LocationHelper)
}
