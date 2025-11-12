package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.schoolCycle.GroupApiCall
import com.mx.liftechnology.data.repository.schoolCycle.menu.MenuLocalRepository
import com.mx.liftechnology.data.repository.schoolCycle.menu.MenuRepository
import com.mx.liftechnology.data.repository.schoolCycle.menu.MenuRepositoryImpl
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.GetControlMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.GetControlRegisterUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.GetGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.GetListPartialMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.UpdateGroupMenuUseCase
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.menu.MenuViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Koin module for schoolCycle-related dependencies.
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
    singleOf(::MenuRepositoryImpl) {
        bind<MenuRepository>()
    }

    /**
     * Provides a singleton instance of [GetControlMenuUseCase].
     */
    singleOf(::GetControlMenuUseCase)

    /**
     * Provides a singleton instance of [GetControlRegisterUseCase].
     */
    singleOf(::GetControlRegisterUseCase)

    /**
     * Provides a singleton instance of [UpdateGroupMenuUseCase].
     */
    singleOf(::UpdateGroupMenuUseCase)

    /**
     * Provides a singleton instance of [GetGroupMenuUseCase].
     */
    singleOf(::GetGroupMenuUseCase)

    /**
     * Provides a singleton instance of [GetListPartialMenuUseCase].
     */
    singleOf(::GetListPartialMenuUseCase)

    /**
     * Provides an instance of [MenuViewModel].
     */
    viewModelOf(::MenuViewModel)
}
