package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.school.GetCctUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.RegisterCycleSchoolUseCase
import com.mx.liftechnology.domain.usecase.school.RegisterSchoolWithValidationUseCase
import com.mx.liftechnology.domain.usecase.school.ValidateFieldsRegisterSchoolUseCase
import com.mx.liftechnology.domain.usecase.school.ValidateFieldsRegisterSchoolUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.school.RegisterSchoolViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val schoolCycleModule = module{

    /**
     * Proporciona una instancia de [GetCctUseCase].
     * Proporciona una instancia singleton de [RegisterSchoolWithValidationUseCase].
     * Proporciona una instancia singleton de [RegisterCycleSchoolUseCase].
     * Proporciona una instancia singleton de [ValidateFieldsRegisterSchoolUseCase].
     */
    factory{GetCctUseCase(get()) }
    singleOf(::RegisterSchoolWithValidationUseCase)
    singleOf(::RegisterCycleSchoolUseCase)
    singleOf(::ValidateFieldsRegisterSchoolUseCaseImp){
        bind<ValidateFieldsRegisterSchoolUseCase>()
    }

    /**
     * Proporciona una instancia de [RegisterSchoolViewModel].
     */
    viewModelOf(::RegisterSchoolViewModel)
}