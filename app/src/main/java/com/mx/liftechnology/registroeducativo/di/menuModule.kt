package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.GroupApiCall
import com.mx.liftechnology.data.repository.mainFlow.MenuLocalRepository
import com.mx.liftechnology.data.repository.mainFlow.MenuRepository
import com.mx.liftechnology.data.repository.mainFlow.MenuRepositoryImp
import com.mx.liftechnology.domain.usecase.flowmenu.MenuUseCase
import com.mx.liftechnology.domain.usecase.flowmenu.MenuUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.MenuViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val menuModule = module {
    factory { get<Retrofit>().create(GroupApiCall::class.java) }


    single{ MenuLocalRepository(androidContext()) }

    single <MenuRepository>{
        MenuRepositoryImp(get())
    }

    single <MenuUseCase>{
        MenuUseCaseImp(get(), get(), get())
    }

    viewModel {
        MenuViewModel(get(), get())
    }
}
