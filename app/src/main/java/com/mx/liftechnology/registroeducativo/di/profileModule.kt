package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for profile-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val profileModule = module {
    /**
     * Proporciona una instancia singleton de [ProfileViewModel].
     */
    viewModelOf(::ProfileViewModel)
}
