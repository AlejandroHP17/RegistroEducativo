package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListAssignmentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.GetPercentSubjectIdApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterJobStudentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterListAssignmentApiCall
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.RegisterAssignmentRepository
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.RegisterAssignmentRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetListAssignmentRepository
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetListAssignmentRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetPercentSubjectRepository
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetPercentSubjectRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.RegisterListAssignmentRepository
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.RegisterListAssignmentRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.SaveIdSubjectSelectedUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.GetDatesActivePartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.GetListAssignmentPerSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.RegisterAssignmentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.RegisterListAssignmentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.RegisterListAssignmentUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.ValidateFieldsAssignmentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.ValidateFieldsAssignmentUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.registerassignment.RegisterAssignmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
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
    single<GetPercentSubjectRepository> {
        GetPercentSubjectRepositoryImp(get())
    }

    /**
     * Provides a singleton instance of [RegisterListAssignmentRepository].
     */
    single<RegisterListAssignmentRepository> {
        RegisterListAssignmentRepositoryImp(get())
    }

    /**
     * Provides a singleton instance of [GetListAssignmentRepository].
     */
    single<GetListAssignmentRepository> {
        GetListAssignmentRepositoryImp(get())
    }

    /**
     * Provides a singleton instance of [RegisterAssignmentRepository].
     */
    single<RegisterAssignmentRepository> {
        RegisterAssignmentRepositoryImp(get())
    }

    /**
     * Provides a singleton instance of [RegisterListAssignmentUseCase].
     */
    single<RegisterListAssignmentUseCase> {
        RegisterListAssignmentUseCaseImp(get(), get())
    }

    /**
     * Provides a singleton instance of [ValidateFieldsAssignmentUseCase].
     */
    single<ValidateFieldsAssignmentUseCase> {
        ValidateFieldsAssignmentUseCaseImp()
    }

    /**
     * Provides a singleton instance of [SaveIdSubjectSelectedUseCase].
     */
    single { SaveIdSubjectSelectedUseCase(get()) }

    /**
     * Provides a singleton instance of [GetListAssignmentPerSubjectUseCase].
     */
    single { GetListAssignmentPerSubjectUseCase(get(),get()) }

    /**
     * Provides a singleton instance of [RegisterAssignmentUseCase].
     */
    single { RegisterAssignmentUseCase(get(), get()) }

    /**
     * Provides a singleton instance of [GetDatesActivePartialUseCase].
     */
    single { GetDatesActivePartialUseCase(get()) }

    /**
     * Provides an instance of [RegisterAssignmentViewModel].
     */
    viewModel { RegisterAssignmentViewModel(get(), get(),get(), get(), get(), get(), get()) }
}
