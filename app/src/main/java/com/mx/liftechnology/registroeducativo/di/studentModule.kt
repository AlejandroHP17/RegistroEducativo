package com.mx.liftechnology.registroeducativo.di

/**
 * @file Define el módulo de Koin para dependencias CRUD relacionadas con estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */

import com.mx.liftechnology.domain.usecase.student.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.student.ValidateVoiceStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.student.DeleteStudentUseCase
import com.mx.liftechnology.domain.usecase.student.EditStudentUseCase
import com.mx.liftechnology.domain.usecase.student.EditStudentWithValidationUseCase
import com.mx.liftechnology.domain.usecase.student.RegisterStudentUseCase
import com.mx.liftechnology.domain.usecase.student.RegisterStudentWithValidationUseCase
import com.mx.liftechnology.registroeducativo.main.ui.student.list.ListStudentViewModel
import com.mx.liftechnology.registroeducativo.main.ui.student.register.RegisterStudentViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


/**
 * Módulo de Koin para dependencias CRUD relacionadas con estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val studentModule = module {
    /**
     * Proporciona una instancia singleton de [ValidateVoiceStudentUseCase].
     */
    singleOf(::ValidateVoiceStudentUseCaseImp) {
        bind<ValidateVoiceStudentUseCase>()
    }

    /**
     * Proporciona una instancia singleton de [RegisterStudentUseCase].
     * Proporciona una instancia singleton de [EditStudentWithValidationUseCase].
     * Proporciona una instancia singleton de [RegisterStudentWithValidationUseCase].
     * Proporciona una instancia singleton de [DeleteStudentUseCase].
     * Proporciona una instancia singleton de [EditStudentUseCase].
     */
    singleOf(::RegisterStudentUseCase)
    singleOf(::EditStudentWithValidationUseCase)
    singleOf(::RegisterStudentWithValidationUseCase)
    singleOf(::DeleteStudentUseCase)
    singleOf(::EditStudentUseCase)

    /**
     * Proporciona una instancia de [RegisterStudentViewModel].
     * Proporciona una instancia de [ListStudentViewModel].
     */
    viewModelOf(::RegisterStudentViewModel)
    viewModelOf(::ListStudentViewModel)
}
