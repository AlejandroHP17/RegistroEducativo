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

/**
 * Módulo de Koin para dependencias relacionadas con ciclos escolares y escuelas.
 * 
 * Este módulo proporciona las instancias necesarias para:
 * - Obtención de CCT (Clave de Centro de Trabajo)
 * - Registro de escuelas con validación
 * - Registro de ciclos escolares
 * - Validación de campos para registro de escuelas
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val schoolCycleModule = module{

    /**
     * Proporciona una instancia factory de [GetCctUseCase].
     * Caso de uso para obtener el CCT (Clave de Centro de Trabajo) de una escuela.
     */
    factory{GetCctUseCase(get()) }
    
    /**
     * Proporciona una instancia singleton de [RegisterSchoolWithValidationUseCase].
     * Caso de uso para registrar una escuela con validación de campos.
     */
    singleOf(::RegisterSchoolWithValidationUseCase)
    
    /**
     * Proporciona una instancia singleton de [RegisterCycleSchoolUseCase].
     * Caso de uso para registrar un ciclo escolar.
     */
    singleOf(::RegisterCycleSchoolUseCase)
    
    /**
     * Proporciona una instancia singleton de [ValidateFieldsRegisterSchoolUseCase].
     * Caso de uso para validar los campos del formulario de registro de escuela.
     */
    singleOf(::ValidateFieldsRegisterSchoolUseCaseImp){
        bind<ValidateFieldsRegisterSchoolUseCase>()
    }

    /**
     * Proporciona una instancia de [RegisterSchoolViewModel].
     */
    viewModelOf(::RegisterSchoolViewModel)
}