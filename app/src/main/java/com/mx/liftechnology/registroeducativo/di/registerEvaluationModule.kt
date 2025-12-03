package com.mx.liftechnology.registroeducativo.di

/**
 * @file Define el módulo de Koin para dependencias relacionadas con el registro de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */

import com.mx.liftechnology.domain.usecase.evaluation.GetDatesActivePartialUseCase
import com.mx.liftechnology.domain.usecase.evaluation.RegisterWorkTypeEvaluationsUseCase
import com.mx.liftechnology.domain.usecase.evaluation.ValidateFieldsEvaluationUseCase
import com.mx.liftechnology.domain.usecase.evaluation.ValidateFieldsEvaluationUseCaseImp
import com.mx.liftechnology.domain.usecase.formativeField.SaveFormativeFieldIdSelectedUseCase
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
val registerEvaluationModule = module {

    /**
     * Proporciona una instancia singleton de [ValidateFieldsEvaluationUseCase].
     */
    singleOf(::ValidateFieldsEvaluationUseCaseImp) {
        bind<ValidateFieldsEvaluationUseCase>()
    }



    /**
     * Proporciona una instancia singleton de [SaveFormativeFieldIdSelectedUseCase].
     */
    singleOf(::SaveFormativeFieldIdSelectedUseCase)

    /**
     * Proporciona una instancia singleton de [GetDatesActivePartialUseCase].
     */
    singleOf(::GetDatesActivePartialUseCase)

    singleOf(::RegisterWorkTypeEvaluationsUseCase)

    /**
     * Proporciona una instancia de [RegisterEvaluationViewModel].
     */
    viewModelOf(::RegisterEvaluationViewModel)
}
