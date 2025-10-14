package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.flowMain.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for profile-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val profileModule = module {
    /**
     * Provides an instance of [ProfileViewModel].
     */
    viewModel { ProfileViewModel(get()) }
}
