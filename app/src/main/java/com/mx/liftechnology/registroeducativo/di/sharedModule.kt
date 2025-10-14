package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for shared dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val sharedModule = module {

    /**
     * Provides an instance of [SharedViewModel].
     */
    viewModel {
        SharedViewModel()
    }
}
