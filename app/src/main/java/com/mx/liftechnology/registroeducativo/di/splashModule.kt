package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.flowSplash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for splash screen dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val splashModule = module {
    /**
     * Provides an instance of [SplashViewModel].
     */
    viewModel { SplashViewModel(get(), get()) }
}
