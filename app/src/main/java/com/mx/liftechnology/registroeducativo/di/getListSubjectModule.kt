package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.GetListSubjectApiCall
import com.mx.liftechnology.data.repository.getFlow.GetListSubjectRepository
import com.mx.liftechnology.data.repository.getFlow.GetListSubjectRepositoryImp
import com.mx.liftechnology.domain.usecase.flowgetdata.GetListSubjectUseCase
import com.mx.liftechnology.domain.usecase.flowgetdata.GetListSubjectUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.subject.ListSubjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val listSubjectModule = module {

    factory { get<Retrofit>().create(GetListSubjectApiCall::class.java) }

    single<GetListSubjectRepository> {
        GetListSubjectRepositoryImp(get())
    }

    single<GetListSubjectUseCase> {
        GetListSubjectUseCaseImp(get(), get())
    }

    viewModel {
        ListSubjectViewModel(get())
    }
}
