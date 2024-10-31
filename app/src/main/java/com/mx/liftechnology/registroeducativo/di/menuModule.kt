package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.data.repository.MenuRepository
import com.mx.liftechnology.domain.usecase.MenuUseCase
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.home.MenuViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val menuModule = module {
    /* Local */
    single { MenuRepository(androidContext()) }

    single {
        MenuUseCase(get())
    }

    viewModel {
        MenuViewModel(get())
    }
}
