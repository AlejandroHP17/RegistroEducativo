package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.data.repositoryImpl.menu.MenuLocalRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.schoolCycle.MenuRepositoryImpl
import com.mx.liftechnology.domain.repository.schoolCycle.menu.MenuRepository
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
     * Provides an instance of [SchoolCycleApi].
     */
    factory { get<Retrofit>().create(SchoolCycleApi::class.java) }

    /**
     * Provides a singleton instance of [MenuLocalRepositoryImpl].
     */
    single { MenuLocalRepositoryImpl(androidContext()) }

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
