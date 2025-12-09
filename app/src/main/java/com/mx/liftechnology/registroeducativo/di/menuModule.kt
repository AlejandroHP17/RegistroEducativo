package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.menu.GetControlMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.GetControlRegisterUseCase
import com.mx.liftechnology.domain.usecase.menu.GetGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.GetListPartialMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.SavePartialMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.UpdateGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.UpdatePartialMenuUseCase
import com.mx.liftechnology.registroeducativo.main.ui.menu.MenuViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val menuModule = module {
        /**
     * Proporciona una instancia singleton de [GetGroupMenuUseCase].
     * Proporciona una instancia singleton de [UpdateGroupMenuUseCase].
     * Proporciona una instancia singleton de [GetControlMenuUseCase].
     * Proporciona una instancia singleton de [GetControlRegisterUseCase].
     * Proporciona una instancia singleton de [GetListPartialMenuUseCase].
     * Proporciona una instancia singleton de [MenuViewModel].
     * Proporciona una instancia singleton de [UpdatePartialMenuUseCase].
     */
    singleOf(::GetGroupMenuUseCase)
    singleOf(::UpdateGroupMenuUseCase)
    singleOf(::GetControlMenuUseCase)
    singleOf(::GetControlRegisterUseCase)
    singleOf(::GetListPartialMenuUseCase)
    singleOf(::SavePartialMenuUseCase)
    singleOf(::UpdatePartialMenuUseCase)

    /**
     * Proporciona una instancia de [MenuViewModel].
     */
    viewModelOf(::MenuViewModel)
}