package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.RegisterListAssignmentApiCall
import com.mx.liftechnology.data.repository.mainflowdata.subject.assignment.CrudAssignmentRepository
import com.mx.liftechnology.data.repository.mainflowdata.subject.assignment.CrudAssignmentRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.RegisterListAssignmentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment.RegisterListAssignmentUseCaseImp
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

    single<CrudAssignmentRepository> {
        CrudAssignmentRepositoryImp(get(), get())
    }

    single<RegisterListAssignmentUseCase> {
        RegisterListAssignmentUseCaseImp(get(), get())
    }

    viewModel { RegisterAssignmentViewModel(get()) }
}
