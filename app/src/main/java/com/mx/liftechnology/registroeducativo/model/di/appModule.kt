package com.mx.liftechnology.registroeducativo.model.di

import com.mx.liftechnology.registroeducativo.main.ui.home.viewmodel.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MenuViewModel()
    }
}
