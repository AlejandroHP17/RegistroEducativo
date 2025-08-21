package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.GetListAssignmentApiCall
import com.mx.liftechnology.core.network.callapi.GetPercentSubjectIdApiCall
import com.mx.liftechnology.core.network.callapi.RegisterListAssignmentApiCall
import com.mx.liftechnology.core.network.callapi.RegisterOneJobStudentApiCall
import com.mx.liftechnology.data.repository.mainflowdata.subject.RegisterAssignmentRepository
import com.mx.liftechnology.data.repository.mainflowdata.subject.RegisterAssignmentRepositoryImp
import com.mx.liftechnology.data.repository.mainflowdata.subject.assignment.CrudAssignmentRepository
import com.mx.liftechnology.data.repository.mainflowdata.subject.assignment.CrudAssignmentRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.SaveIdSubjectSelectedUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.GetListAssignmentPerSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.RegisterAssignmentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.RegisterListAssignmentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.RegisterListAssignmentUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.ValidateFieldsAssignmentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.ValidateFieldsAssignmentUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.registerassignment.RegisterAssignmentViewModel
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
    factory { get<Retrofit>().create(RegisterOneJobStudentApiCall::class.java) }

    single<CrudAssignmentRepository> {
        CrudAssignmentRepositoryImp(get(), get(), get())
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

    viewModel { RegisterAssignmentViewModel(get(), get(),get(), get(), get(), get()) }
}
