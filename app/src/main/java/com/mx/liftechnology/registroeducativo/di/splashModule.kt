package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.activitySplash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/** DI to login
 * @author pelkidev
 * @since 1.0.0
 */
val splashModule = module {

    viewModel {
        SplashViewModel(get())
    }
}
