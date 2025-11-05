package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListAssignmentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.GetPercentSubjectIdApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterJobStudentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterListAssignmentApiCall
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetListAssignmentRepository
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetListAssignmentRepositoryImpl
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetPercentSubjectRepository
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetPercentSubjectRepositoryImpl
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.RegisterAssignmentRepository
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.RegisterAssignmentRepositoryImpl
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.RegisterListAssignmentRepository
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.RegisterListAssignmentRepositoryImpl
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.SaveIdSubjectSelectedUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.GetDatesActivePartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.GetListAssignmentPerSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.RegisterAssignmentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.RegisterListAssignmentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.RegisterListAssignmentUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.ValidateFieldsAssignmentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.ValidateFieldsAssignmentUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.registerassignment.RegisterAssignmentViewModel
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
     * Provides an instance of [RegisterListAssignmentApiCall].
     */
    factory { get<Retrofit>().create(RegisterListAssignmentApiCall::class.java) }

    /**
     * Provides an instance of [GetPercentSubjectIdApiCall].
     */
    factory { get<Retrofit>().create(GetPercentSubjectIdApiCall::class.java) }

    /**
     * Provides an instance of [GetListAssignmentApiCall].
     */
    factory { get<Retrofit>().create(GetListAssignmentApiCall::class.java) }

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
     * Provides a singleton instance of [RegisterListAssignmentRepository].
     */
    singleOf(::RegisterListAssignmentRepositoryImpl) {
        bind<RegisterListAssignmentRepository>()
    }

    /**
     * Provides a singleton instance of [GetListAssignmentRepository].
     */
    singleOf(::GetListAssignmentRepositoryImpl) {
        bind<GetListAssignmentRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterAssignmentRepository].
     */
    singleOf(::RegisterAssignmentRepositoryImpl) {
        bind<RegisterAssignmentRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterListAssignmentUseCase].
     */
    singleOf(::RegisterListAssignmentUseCaseImp) {
        bind<RegisterListAssignmentUseCase>()
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
     * Provides an instance of [RegisterAssignmentViewModel].
     */
    viewModelOf(::RegisterAssignmentViewModel)
}
