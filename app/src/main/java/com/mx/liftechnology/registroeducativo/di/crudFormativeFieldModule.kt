package com.mx.liftechnology.registroeducativo.di

/**
 * @file Define el módulo de Koin para dependencias CRUD relacionadas con materias.
 */

import com.mx.liftechnology.domain.usecase.evaluation.GetListWorkEvaluationFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.formativeField.DeleteFormativeFieldsUseCase
import com.mx.liftechnology.domain.usecase.formativeField.GetListFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.formativeField.GetListWorkTypeUseCase
import com.mx.liftechnology.domain.usecase.formativeField.RegisterFormativeFieldsBulkUseCase
import com.mx.liftechnology.domain.usecase.formativeField.ValidateFieldsFormativeFieldsUseCase
import com.mx.liftechnology.domain.usecase.formativeField.ValidateFieldsFormativeFieldsUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.formativeFields.list.ListFormativeFieldsViewModel
import com.mx.liftechnology.registroeducativo.main.ui.formativeFields.register.RegisterFormativeFieldsViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


/**
 * Módulo de Koin para dependencias CRUD relacionadas con materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val crudFormativeFieldModule = module {


    /**
     * Proporciona una instancia singleton de [GetListWorkEvaluationFormativeFieldUseCase].
     */
    singleOf(::GetListWorkEvaluationFormativeFieldUseCase)

    /**
     * Proporciona una instancia singleton de [RegisterFormativeFieldsBulkUseCase].
     */
    singleOf(::RegisterFormativeFieldsBulkUseCase)

    /**
     * Proporciona una instancia singleton de [GetListFormativeFieldUseCase].
     */
    singleOf(::GetListFormativeFieldUseCase)

    /**
     * Proporciona una instancia singleton de [GetListWorkTypeUseCase].
     */
    singleOf(::GetListWorkTypeUseCase)

    /**
     * Proporciona una instancia singleton de [DeleteFormativeFieldsUseCase].
     */
    singleOf(::DeleteFormativeFieldsUseCase)

    /**
     * Proporciona una instancia singleton de [ValidateFieldsFormativeFieldsUseCase].
     */
    singleOf(::ValidateFieldsFormativeFieldsUseCaseImp) {
        bind<ValidateFieldsFormativeFieldsUseCase>()
    }

    /**
     * Proporciona una instancia de [RegisterFormativeFieldsViewModel].
     */
    viewModelOf(::RegisterFormativeFieldsViewModel)

    /**
     * Proporciona una instancia de [ListFormativeFieldsViewModel].
     */
    viewModelOf(::ListFormativeFieldsViewModel)
}
