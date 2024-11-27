package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.partial.RegisterPartialViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/** DI to register user and get CCT
 * @author pelkidev
 * @since 1.0.0
 */
val registerPartialModule = module {

    viewModel {
        RegisterPartialViewModel(get())
    }
}
