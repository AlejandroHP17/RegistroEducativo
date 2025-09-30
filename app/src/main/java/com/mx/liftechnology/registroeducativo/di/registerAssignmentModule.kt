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

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val registerAssignmentModule = module {

    factory { get<Retrofit>().create(RegisterListAssignmentApiCall::class.java) }
    factory { get<Retrofit>().create(GetPercentSubjectIdApiCall::class.java) }
    factory { get<Retrofit>().create(GetListAssignmentApiCall::class.java) }
    factory { get<Retrofit>().create(GetListAssignmentApiCall::class.java) }
    factory { get<Retrofit>().create(RegisterJobStudentApiCall::class.java) }

    single<GetPercentSubjectRepository> {
        GetPercentSubjectRepositoryImp(get())
    }
    single<RegisterListAssignmentRepository> {
        RegisterListAssignmentRepositoryImp(get())
    }
    single<GetListAssignmentRepository> {
        GetListAssignmentRepositoryImp(get())
    }
    single<RegisterAssignmentRepository> {
        RegisterAssignmentRepositoryImp(get())
    }

    single<RegisterListAssignmentUseCase> {
        RegisterListAssignmentUseCaseImp(get(), get())
    }
    single<ValidateFieldsAssignmentUseCase> {
        ValidateFieldsAssignmentUseCaseImp()
    }
    single { SaveIdSubjectSelectedUseCase(get()) }
    single { GetListAssignmentPerSubjectUseCase(get(),get()) }
    single { RegisterAssignmentUseCase(get(), get()) }
    single { GetDatesActivePartialUseCase(get()) }

    viewModel { RegisterAssignmentViewModel(get(), get(),get(), get(), get(), get(), get()) }
}
