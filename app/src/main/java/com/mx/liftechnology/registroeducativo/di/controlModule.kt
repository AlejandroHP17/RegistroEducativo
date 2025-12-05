package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.control.ControlViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Módulo de Koin para dependencias relacionadas con el control de APIs.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val controlModule = module {
    /**
     * Proporciona una instancia de [ControlViewModel].
     */
    viewModelOf(::ControlViewModel)
}
