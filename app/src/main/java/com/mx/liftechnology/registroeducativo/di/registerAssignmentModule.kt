package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.evaluation.RegisterJobStudentApiCall
import com.mx.liftechnology.data.repository.evaluation.GetPercentSubjectRepository
import com.mx.liftechnology.data.repository.evaluation.GetPercentSubjectRepositoryImpl
import com.mx.liftechnology.data.repository.evaluation.RegisterAssignmentRepository
import com.mx.liftechnology.data.repository.evaluation.RegisterAssignmentRepositoryImpl
import com.mx.liftechnology.domain.usecase.evaluation.GetDatesActivePartialUseCase
import com.mx.liftechnology.domain.usecase.evaluation.GetListAssignmentPerSubjectUseCase
import com.mx.liftechnology.domain.usecase.evaluation.RegisterAssignmentUseCase
import com.mx.liftechnology.domain.usecase.evaluation.ValidateFieldsAssignmentUseCase
import com.mx.liftechnology.domain.usecase.evaluation.ValidateFieldsAssignmentUseCaseImp
import com.mx.liftechnology.domain.usecase.formativeField.SaveIdSubjectSelectedUseCase
import com.mx.liftechnology.registroeducativo.main.ui.evaluation.RegisterEvaluationViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Koin module for assignment registration-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val registerAssignmentModule = module {


    /**
     * Provides an instance of [RegisterJobStudentApiCall].
     */
    factory { get<Retrofit>().create(RegisterJobStudentApiCall::class.java) }

    /**
     * Provides a singleton instance of [GetPercentSubjectRepository].
     */
    singleOf(::GetPercentSubjectRepositoryImpl) {
        bind<GetPercentSubjectRepository>()
    }



    /**
     * Provides a singleton instance of [RegisterAssignmentRepository].
     */
    singleOf(::RegisterAssignmentRepositoryImpl) {
        bind<RegisterAssignmentRepository>()
    }



    /**
     * Provides a singleton instance of [ValidateFieldsAssignmentUseCase].
     */
    singleOf(::ValidateFieldsAssignmentUseCaseImp) {
        bind<ValidateFieldsAssignmentUseCase>()
    }

    /**
     * Provides a singleton instance of [SaveIdSubjectSelectedUseCase].
     */
    singleOf(::SaveIdSubjectSelectedUseCase)

    /**
     * Provides a singleton instance of [GetListAssignmentPerSubjectUseCase].
     */
    singleOf(::GetListAssignmentPerSubjectUseCase)

    /**
     * Provides a singleton instance of [RegisterAssignmentUseCase].
     */
    singleOf(::RegisterAssignmentUseCase)

    /**
     * Provides a singleton instance of [GetDatesActivePartialUseCase].
     */
    singleOf(::GetDatesActivePartialUseCase)

    /**
     * Provides an instance of [RegisterEvaluationViewModel].
     */
    viewModelOf(::RegisterEvaluationViewModel)
}
