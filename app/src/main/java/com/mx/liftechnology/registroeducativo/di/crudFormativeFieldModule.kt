package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.repository.evaluation.GetListWorkTypeFormativeFieldRepository
import com.mx.liftechnology.data.repository.evaluation.GetListWorkTypeFormativeFieldRepositoryImpl
import com.mx.liftechnology.data.repository.formativeField.DeleteFormativeFieldRepository
import com.mx.liftechnology.data.repository.formativeField.DeleteFormativeFieldRepositoryImpl
import com.mx.liftechnology.data.repository.formativeField.GetListFormativeFieldRepository
import com.mx.liftechnology.data.repository.formativeField.GetListFormativeFieldRepositoryImpl
import com.mx.liftechnology.data.repository.formativeField.GetWorkTypeRepository
import com.mx.liftechnology.data.repository.formativeField.GetWorkTypeRepositoryImpl
import com.mx.liftechnology.data.repository.formativeField.RegisterFormativeFieldsBulkRepository
import com.mx.liftechnology.data.repository.formativeField.RegisterFormativeFieldsBulkRepositoryImpl
import com.mx.liftechnology.domain.usecase.evaluation.GetListWorkEvaluationFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.formativeField.DeleteFormativeFieldsUseCase
import com.mx.liftechnology.domain.usecase.formativeField.GetListSubjectUseCase
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
import retrofit2.Retrofit

/**
 * Koin module for subject-related CRUD dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val crudFormativeFieldModule = module {

    /**
     * Provides an instance of [FormativeFieldApi].
     */
    factory { get<Retrofit>().create(FormativeFieldApi::class.java) }

    /**
     * Provides an instance of [EvaluationApi].
     */
    factory { get<Retrofit>().create(EvaluationApi::class.java) }

    /**
     * Provides a singleton instance of [GetListWorkTypeFormativeFieldRepository].
     */
    singleOf(::GetListWorkTypeFormativeFieldRepositoryImpl) {
        bind<GetListWorkTypeFormativeFieldRepository>()
    }

    /**
     * Provides a singleton instance of [GetListFormativeFieldRepository].
     */
    singleOf(::GetListFormativeFieldRepositoryImpl) {
        bind<GetListFormativeFieldRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterFormativeFieldsBulkRepository].
     */
    singleOf(::RegisterFormativeFieldsBulkRepositoryImpl) {
        bind<RegisterFormativeFieldsBulkRepository>()
    }

    /**
     * Provides a singleton instance of [GetWorkTypeRepository].
     */
    singleOf(::GetWorkTypeRepositoryImpl) {
        bind<GetWorkTypeRepository>()
    }
    /**
     * Provides a singleton instance of [GetWorkTypeRepository].
     */
    singleOf(::DeleteFormativeFieldRepositoryImpl) {
        bind<DeleteFormativeFieldRepository>()
    }

    /**
     * Provides a singleton instance of [GetListWorkEvaluationFormativeFieldUseCase].
     */
    singleOf(::GetListWorkEvaluationFormativeFieldUseCase)

    /**
     * Provides a singleton instance of [RegisterFormativeFieldsBulkUseCase].
     */
    singleOf(::RegisterFormativeFieldsBulkUseCase)

    /**
     * Provides a singleton instance of [GetListSubjectUseCase].
     */
    singleOf(::GetListSubjectUseCase)

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
