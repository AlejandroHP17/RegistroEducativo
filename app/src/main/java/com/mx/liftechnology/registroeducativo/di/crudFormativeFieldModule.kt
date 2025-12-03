package com.mx.liftechnology.registroeducativo.di


import com.mx.liftechnology.data.repositoryImpl.evaluation.GetListWorkTypeFormativeFieldRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.formativeField.DeleteFormativeFieldRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.formativeField.GetListFormativeFieldRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.workType.GetWorkTypeRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.formativeField.RegisterFormativeFieldsBulkRepositoryImpl
import com.mx.liftechnology.domain.repository.evaluation.GetListWorkTypeFormativeFieldRepository
import com.mx.liftechnology.domain.repository.formativeFields.DeleteFormativeFieldRepository
import com.mx.liftechnology.domain.repository.formativeFields.GetListFormativeFieldRepository
import com.mx.liftechnology.domain.repository.formativeFields.GetWorkTypeRepository
import com.mx.liftechnology.domain.repository.formativeFields.RegisterFormativeFieldsBulkRepository
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
 * Koin module for subject-related CRUD dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val crudFormativeFieldModule = module {


    /**
     * Provides a singleton instance of [GetListWorkEvaluationFormativeFieldUseCase].
     */
    singleOf(::GetListWorkEvaluationFormativeFieldUseCase)

    /**
     * Provides a singleton instance of [RegisterFormativeFieldsBulkUseCase].
     */
    singleOf(::RegisterFormativeFieldsBulkUseCase)

    /**
     * Provides a singleton instance of [GetListFormativeFieldUseCase].
     */
    singleOf(::GetListFormativeFieldUseCase)

    /**
     * Provides a singleton instance of [GetListWorkTypeUseCase].
     */
    singleOf(::GetListWorkTypeUseCase)

    /**
     * Provides a singleton instance of [GetListWorkTypeUseCase].
     */
    singleOf(::DeleteFormativeFieldsUseCase)

    /**
     * Provides a singleton instance of [ValidateFieldsFormativeFieldsUseCase].
     */
    singleOf(::ValidateFieldsFormativeFieldsUseCaseImp) {
        bind<ValidateFieldsFormativeFieldsUseCase>()
    }

    /**
     * Provides an instance of [RegisterFormativeFieldsViewModel].
     */
    viewModelOf(::RegisterFormativeFieldsViewModel)

    /**
     * Provides an instance of [ListFormativeFieldsViewModel].
     */
    viewModelOf(::ListFormativeFieldsViewModel)
}
