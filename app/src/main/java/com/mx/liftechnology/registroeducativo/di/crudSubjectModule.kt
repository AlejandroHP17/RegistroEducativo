package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListEvaluationTypeApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.GetListWorkTypeApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.formativeField.DeleteFormativeFieldsApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.formativeField.GetListFormativeFieldsApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.formativeField.RegisterFormativeFieldsBulkApiCall
import com.mx.liftechnology.data.repository.flowMain.formativeFields.DeleteFormativeFieldsRepository
import com.mx.liftechnology.data.repository.flowMain.formativeFields.DeleteFormativeFieldsRepositoryImpl
import com.mx.liftechnology.data.repository.flowMain.formativeFields.GetListFormativeFieldsRepository
import com.mx.liftechnology.data.repository.flowMain.formativeFields.GetListFormativeFieldsRepositoryImpl
import com.mx.liftechnology.data.repository.flowMain.formativeFields.RegisterFormativeFieldsBulkRepository
import com.mx.liftechnology.data.repository.flowMain.formativeFields.RegisterFormativeFieldsBulkRepositoryImpl
import com.mx.liftechnology.data.repository.flowMain.formativeFields.evaluationtype.GetListEvaluationTypeRepository
import com.mx.liftechnology.data.repository.flowMain.formativeFields.evaluationtype.GetListEvaluationTypeRepositoryImpl
import com.mx.liftechnology.data.repository.flowMain.formativeFields.workType.GetWorkTypeRepository
import com.mx.liftechnology.data.repository.flowMain.formativeFields.workType.GetWorkTypeRepositoryImpl
import com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields.DeleteFormativeFieldsUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields.GetListSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields.RegisterFormativeFieldsBulkUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields.ValidateFieldsSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields.ValidateFieldsSubjectUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields.evaluationType.GetListEvaluationTypeUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields.evaluationType.GetListEvaluationTypeUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields.workType.GetListWorkTypeUseCase
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.formativeFields.list.ListFormativeFieldsViewModel
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.formativeFields.register.RegisterFormativeFieldsViewModel
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
val crudSubjectModule = module {

    /**
     * Provides an instance of [RegisterFormativeFieldsBulkApiCall].
     */
    factory { get<Retrofit>().create(RegisterFormativeFieldsBulkApiCall::class.java) }

    /**
     * Provides an instance of [GetListFormativeFieldsApiCall].
     */
    factory { get<Retrofit>().create(GetListFormativeFieldsApiCall::class.java) }

    /**
     * Provides an instance of [GetListEvaluationTypeApiCall].
     */
    factory { get<Retrofit>().create(GetListEvaluationTypeApiCall::class.java) }

    /**
     * Provides an instance of [GetListWorkTypeApiCall].
     */
    factory { get<Retrofit>().create(GetListWorkTypeApiCall::class.java) }

    /**
     * Provides an instance of [GetListWorkTypeApiCall].
     */
    factory { get<Retrofit>().create(DeleteFormativeFieldsApiCall::class.java) }

    /**
     * Provides a singleton instance of [GetListEvaluationTypeRepository].
     */
    singleOf(::GetListEvaluationTypeRepositoryImpl) {
        bind<GetListEvaluationTypeRepository>()
    }

    /**
     * Provides a singleton instance of [GetListFormativeFieldsRepository].
     */
    singleOf(::GetListFormativeFieldsRepositoryImpl) {
        bind<GetListFormativeFieldsRepository>()
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
    singleOf(::DeleteFormativeFieldsRepositoryImpl) {
        bind<DeleteFormativeFieldsRepository>()
    }

    /**
     * Provides a singleton instance of [GetListEvaluationTypeUseCase].
     */
    singleOf(::GetListEvaluationTypeUseCaseImp) {
        bind<GetListEvaluationTypeUseCase>()
    }

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
     * Provides a singleton instance of [ValidateFieldsSubjectUseCase].
     */
    singleOf(::ValidateFieldsSubjectUseCaseImp) {
        bind<ValidateFieldsSubjectUseCase>()
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
