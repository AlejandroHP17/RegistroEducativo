package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val sharedModule = module {

    viewModel {
        SharedViewModel()
    }
}
