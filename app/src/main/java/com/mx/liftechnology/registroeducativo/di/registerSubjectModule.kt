package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.RegisterSubjectApiCall
import com.mx.liftechnology.data.repository.registerFlow.RegisterSubjectRepository
import com.mx.liftechnology.data.repository.registerFlow.RegisterSubjectRepositoryImp
import com.mx.liftechnology.domain.usecase.flowregisterdata.RegisterSubjectUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.RegisterSubjectUseCaseImp
import com.mx.liftechnology.domain.usecase.flowregisterdata.ValidateFieldsSubjectUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.ValidateFieldsSubjectUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.subject.RegisterSubjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val registerSubjectModule = module {

    factory { get<Retrofit>().create(RegisterSubjectApiCall::class.java) }

    single<RegisterSubjectRepository> {
        RegisterSubjectRepositoryImp(get())
    }

    single<RegisterSubjectUseCase> {
        RegisterSubjectUseCaseImp(get(), get())
    }
    single<ValidateFieldsSubjectUseCase> {
        ValidateFieldsSubjectUseCaseImp()
    }

    viewModel {
        RegisterSubjectViewModel(get(), get(), get())
    }
}
