package com.mx.liftechnology.registroeducativo.model.di

import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val loginModule = module {


    viewModel {
        LoginViewModel()
    }
}
