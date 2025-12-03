package com.mx.liftechnology.registroeducativo.di


/**
 * @file Define el módulo de Koin para dependencias relacionadas con el ciclo escolar.
 * @author Pelkidev
 * @version 1.0.0
 */
import com.mx.liftechnology.data.repositoryImpl.menu.MenuLocalRepositoryImpl
import com.mx.liftechnology.domain.repository.schoolCycle.menu.MenuLocalRepository
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.GetControlMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.GetControlRegisterUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.GetGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.GetListPartialMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.UpdateGroupMenuUseCase
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.menu.MenuViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


/**
 * Módulo de Koin para dependencias relacionadas con el ciclo escolar.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val menuModule = module {


    /**
     * Proporciona una instancia singleton de [MenuLocalRepositoryImpl].
     */
    single <MenuLocalRepository>{ MenuLocalRepositoryImpl(androidContext()) }



    /**
     * Proporciona una instancia singleton de [GetControlMenuUseCase].
     */
    singleOf(::GetControlMenuUseCase)

    /**
     * Proporciona una instancia singleton de [GetControlRegisterUseCase].
     */
    singleOf(::GetControlRegisterUseCase)

    /**
     * Proporciona una instancia singleton de [UpdateGroupMenuUseCase].
     */
    singleOf(::UpdateGroupMenuUseCase)

    /**
     * Proporciona una instancia singleton de [GetGroupMenuUseCase].
     */
    singleOf(::GetGroupMenuUseCase)

    /**
     * Proporciona una instancia singleton de [GetListPartialMenuUseCase].
     */
    singleOf(::GetListPartialMenuUseCase)

    /**
     * Proporciona una instancia de [MenuViewModel].
     */
    viewModelOf(::MenuViewModel)
}
