package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowMain.GroupApiCall
import com.mx.liftechnology.data.repository.flowMain.menu.MenuLocalRepository
import com.mx.liftechnology.data.repository.flowMain.menu.MenuRepository
import com.mx.liftechnology.data.repository.flowMain.menu.MenuRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.GetControlMenuUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.GetControlRegisterUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.GetGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.GetListPartialMenuUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.UpdateGroupMenuUseCase
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.menu.MenuViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Koin module for menu-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val menuModule = module {

    /**
     * Provides an instance of [GroupApiCall].
     */
    factory { get<Retrofit>().create(GroupApiCall::class.java) }

    /**
     * Provides a singleton instance of [MenuLocalRepository].
     */
    single { MenuLocalRepository(androidContext()) }

    /**
     * Provides a singleton instance of [MenuRepository].
     */
    single<MenuRepository> {
        MenuRepositoryImp(get())
    }

    /**
     * Provides a singleton instance of [GetControlMenuUseCase].
     */
    single {
        GetControlMenuUseCase(get())
    }

    /**
     * Provides a singleton instance of [GetControlRegisterUseCase].
     */
    single {
        GetControlRegisterUseCase(get())
    }

    /**
     * Provides a singleton instance of [UpdateGroupMenuUseCase].
     */
    single{
        UpdateGroupMenuUseCase(get())

    }

    /**
     * Provides a singleton instance of [GetGroupMenuUseCase].
     */
    single {
        GetGroupMenuUseCase(get(), get())
    }

    /**
     * Provides a singleton instance of [GetListPartialMenuUseCase].
     */
    single {
        GetListPartialMenuUseCase(get(), get())
    }

    /**
     * Provides an instance of [MenuViewModel].
     */
    viewModel {
        MenuViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get())
    }
}
