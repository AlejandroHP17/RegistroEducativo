package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.forgetPassword.ForgetPasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/** DI to login
 * @author pelkidev
 * @since 1.0.0
 */
val forgetPasswordModule = module {


    viewModel {
        ForgetPasswordViewModel(get(),get())
    }
}
