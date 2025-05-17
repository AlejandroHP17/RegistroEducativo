package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.GroupApiCall
import com.mx.liftechnology.data.repository.mainflowdata.MenuLocalRepository
import com.mx.liftechnology.data.repository.mainflowdata.MenuRepository
import com.mx.liftechnology.data.repository.mainflowdata.MenuRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.GetControlMenuUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.GetControlRegisterUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.GetGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.GetListPartialMenuUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.UpdateGroupMenuUseCase
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

    single { MenuLocalRepository(androidContext()) }

    single<MenuRepository> {
        MenuRepositoryImp(get())
    }

    single {
        GetControlMenuUseCase(get())
    }
    single {
        GetControlRegisterUseCase(get())
    }

    single{
        UpdateGroupMenuUseCase(get())

    }
    single {
        GetGroupMenuUseCase(get(), get())
    }

    single {
        GetListPartialMenuUseCase(get(), get())
    }

    viewModel {
        MenuViewModel(get(), get(), get(), get(), get(), get(), get(), get())
    }
}
