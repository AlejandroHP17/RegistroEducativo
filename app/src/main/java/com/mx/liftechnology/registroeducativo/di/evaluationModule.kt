package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.evaluation.GetDatesActivePartialUseCase
import com.mx.liftechnology.domain.usecase.evaluation.GetWorkTypeByFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.evaluation.RegisterEvaluationWithValidationUseCase
import com.mx.liftechnology.domain.usecase.evaluation.RegisterWorkTypeEvaluationsUseCase
import com.mx.liftechnology.domain.usecase.evaluation.ValidateFieldsEvaluationUseCase
import com.mx.liftechnology.domain.usecase.evaluation.ValidateFieldsEvaluationUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.evaluation.RegisterEvaluationViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Módulo de Koin para dependencias relacionadas con el registro de asignaciones.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val evaluationModule = module{


    /**
     * Proporciona una instancia singleton de [ValidateFieldsEvaluationUseCase].
     */
    singleOf(::ValidateFieldsEvaluationUseCaseImp) {
        bind<ValidateFieldsEvaluationUseCase>()
    }

    /**
     * Proporciona una instancia singleton de [GetDatesActivePartialUseCase].
     * Proporciona una instancia singleton de [GetWorkTypeByFormativeFieldUseCase].
     * Proporciona una instancia singleton de [RegisterEvaluationWithValidationUseCase].
     * Proporciona una instancia singleton de [RegisterWorkTypeEvaluationsUseCase].
     */
    singleOf(::GetDatesActivePartialUseCase)
    singleOf(::GetWorkTypeByFormativeFieldUseCase)
    singleOf(::RegisterEvaluationWithValidationUseCase)
    singleOf(::RegisterWorkTypeEvaluationsUseCase)

    /**
     * Proporciona una instancia de [RegisterEvaluationViewModel].
     */
    viewModelOf(::RegisterEvaluationViewModel)
}