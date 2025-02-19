package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.GetListSubjectApiCall
import com.mx.liftechnology.core.network.callapi.RegisterSubjectApiCall
import com.mx.liftechnology.data.repository.registerFlow.CrudSubjectRepository
import com.mx.liftechnology.data.repository.registerFlow.CrudSubjectRepositoryImp
import com.mx.liftechnology.domain.usecase.flowdata.subject.ValidateFieldsSubjectUseCase
import com.mx.liftechnology.domain.usecase.flowdata.subject.ValidateFieldsSubjectUseCaseImp
import com.mx.liftechnology.domain.usecase.flowdata.subject.CreateSubjectUseCase
import com.mx.liftechnology.domain.usecase.flowdata.subject.CreateSubjectUseCaseImp
import com.mx.liftechnology.domain.usecase.flowdata.subject.ReadSubjectUseCase
import com.mx.liftechnology.domain.usecase.flowdata.subject.ReadSubjectUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.subject.ListSubjectViewModel
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.subject.RegisterSubjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val crudSubjectModule = module {

    factory { get<Retrofit>().create(RegisterSubjectApiCall::class.java) }
    factory { get<Retrofit>().create(GetListSubjectApiCall::class.java) }

    single<CrudSubjectRepository> {
        CrudSubjectRepositoryImp(get(), get())
    }

    single<CreateSubjectUseCase> {
        CreateSubjectUseCaseImp(get(), get())
    }
    single<ReadSubjectUseCase> {
        ReadSubjectUseCaseImp(get(), get())
    }

    single<ValidateFieldsSubjectUseCase> {
        ValidateFieldsSubjectUseCaseImp()
    }

    viewModel {
        RegisterSubjectViewModel(get(), get(), get())
    }
    viewModel {
        ListSubjectViewModel(get())
    }
}
