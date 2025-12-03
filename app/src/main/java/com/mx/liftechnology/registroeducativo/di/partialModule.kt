package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.partial.RegisterListPartialUseCase
import com.mx.liftechnology.domain.usecase.partial.RegisterPartialWithValidationUseCase
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.partial.RegisterPartialViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val partialModule = module {

    /**
     * Proporciona una instancia singleton de [RegisterPartialWithValidationUseCase].
     * Proporciona una instancia singleton de [RegisterListPartialUseCase].
     * Proporciona una instancia singleton de [GetListPartialUseCase].
     */
    singleOf(::RegisterPartialWithValidationUseCase)
    singleOf(::RegisterListPartialUseCase)
    singleOf(::GetListPartialUseCase)

    /**
     * Proporciona una instancia de [RegisterPartialViewModel].
     */
    viewModelOf(::RegisterPartialViewModel)
}