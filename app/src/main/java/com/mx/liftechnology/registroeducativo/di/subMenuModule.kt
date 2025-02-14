package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.data.repository.mainFlow.SubMenuRepository
import com.mx.liftechnology.domain.usecase.flowmenu.SubMenuUseCase
import com.mx.liftechnology.domain.usecase.flowmenu.SubMenuUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.submenu.SubMenuViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val subMenuModule = module {

    single { SubMenuRepository(androidContext()) }

    single<SubMenuUseCase> {
        SubMenuUseCaseImp(get(), get())
    }

    viewModel {
       SubMenuViewModel(get(), get())
    }
}
