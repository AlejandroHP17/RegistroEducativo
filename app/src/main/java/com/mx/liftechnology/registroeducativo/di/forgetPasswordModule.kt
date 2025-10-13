package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.flowLogin.forgetPassword.ForgetPasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for "forget password"-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val forgetPasswordModule = module {
    /**
     * Provides an instance of [ForgetPasswordViewModel].
     */
    viewModel { ForgetPasswordViewModel(get(), get()) }
}
