package com.mx.liftechnology.registroeducativo.di

/**
 * @file Define el módulo de Koin para dependencias CRUD relacionadas con parciales.
 * @author Pelkidev
 * @version 1.0.0
 */

import com.mx.liftechnology.domain.usecase.schoolCycle.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.RegisterListPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.SavePartialMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.UpdatePartialMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.ValidateFieldsRegisterPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.ValidateFieldsRegisterPartialUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.partial.RegisterPartialViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Módulo de Koin para dependencias CRUD relacionadas con parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val crudPartialModule = module {




    /**
     * Proporciona una instancia singleton de [RegisterListPartialUseCase].
     */
    singleOf(::RegisterListPartialUseCase)

    /**
     * Proporciona una instancia singleton de [GetListPartialUseCase].
     */
    singleOf(::GetListPartialUseCase)

    /**
     * Proporciona una instancia singleton de [ValidateFieldsRegisterPartialUseCase].
     */
    singleOf(::ValidateFieldsRegisterPartialUseCaseImp) {
        bind<ValidateFieldsRegisterPartialUseCase>()
    }

    /**
     * Proporciona una instancia singleton de [SavePartialMenuUseCase].
     */
    singleOf(::SavePartialMenuUseCase)

    /**
     * Proporciona una instancia singleton de [UpdatePartialMenuUseCase].
     */
    singleOf(::UpdatePartialMenuUseCase)

    /**
     * Proporciona una instancia de [RegisterPartialViewModel].
     */
    viewModelOf(::RegisterPartialViewModel)
}
