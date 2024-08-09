package com.mx.liftechnology.registroeducativo.model.di

import com.mx.liftechnology.registroeducativo.data.local.repository.MenuRepository
import com.mx.liftechnology.registroeducativo.main.ui.home.MenuViewModel
import com.mx.liftechnology.registroeducativo.model.usecase.MenuUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {

    /* Local */
    single{
        MenuRepository(androidContext())
    }

    single {
        MenuUseCase(get())
    }

    viewModel {
        MenuViewModel(get())
    }
}
