package com.mx.liftechnology.registroeducativo.di

/**
 * @file Define el módulo de Koin para dependencias relacionadas con el registro de escuela.
 * @author Pelkidev
 * @version 1.0.0
 */

import com.mx.liftechnology.domain.usecase.schoolCycle.school.GetCctUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.RegisterCycleSchoolUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.ValidateFieldsRegisterSchoolUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.ValidateFieldsRegisterSchoolUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.school.RegisterSchoolViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


/**
 * Módulo de Koin para dependencias relacionadas con el registro de escuela.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val registerSchoolModule = module {


    /**
     * Proporciona una instancia de [GetCctUseCase].
     */
    factory{GetCctUseCase(get()) }





    /**
     * Proporciona una instancia de [RegisterCycleSchoolUseCase].
     */
    singleOf(::RegisterCycleSchoolUseCase)

    /**
     * Proporciona una instancia singleton de [ValidateFieldsRegisterSchoolUseCase].
     */
    singleOf(::ValidateFieldsRegisterSchoolUseCaseImp){
        bind<ValidateFieldsRegisterSchoolUseCase>()
    }

    /**
     * Proporciona una instancia de [RegisterSchoolViewModel].
     */
    viewModelOf(::RegisterSchoolViewModel)
}
