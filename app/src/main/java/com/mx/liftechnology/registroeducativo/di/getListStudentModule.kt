package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.GetListStudentApiCall
import com.mx.liftechnology.data.repository.getFlow.GetListStudentRepository
import com.mx.liftechnology.data.repository.getFlow.GetListStudentRepositoryImp
import com.mx.liftechnology.domain.usecase.flowgetdata.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.flowgetdata.GetListStudentUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.student.ListStudentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val getListStudentModule = module {

    factory { get<Retrofit>().create(GetListStudentApiCall::class.java) }

    single<GetListStudentRepository> {
        GetListStudentRepositoryImp(get())
    }

    single<GetListStudentUseCase> {
        GetListStudentUseCaseImp(get(), get())
    }

    viewModel {
        ListStudentViewModel(get())
    }
}
