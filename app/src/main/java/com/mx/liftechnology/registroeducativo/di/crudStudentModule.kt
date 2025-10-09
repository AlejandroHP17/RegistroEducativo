package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListStudentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterStudentApiCall
import com.mx.liftechnology.data.repository.flowMain.student.GetStudentRepository
import com.mx.liftechnology.data.repository.flowMain.student.GetStudentRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.student.RegisterStudentRepository
import com.mx.liftechnology.data.repository.flowMain.student.RegisterStudentRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.ValidateVoiceStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ModifyOneStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ModifyOneStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.RegisterOneStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ValidateFieldsStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ValidateFieldsStudentUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.list.ListStudentViewModel
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.register.RegisterStudentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val crudStudentModule = module {

    factory { get<Retrofit>().create(RegisterStudentApiCall::class.java) }
    factory { get<Retrofit>().create(GetListStudentApiCall::class.java) }

    single<RegisterStudentRepository> {
        RegisterStudentRepositoryImp(get())
    }
    single<GetStudentRepository> {
        GetStudentRepositoryImp(get())
    }

    single {RegisterOneStudentUseCase(get(), get())}

    single{GetListStudentUseCase(get(), get())}

    single<ModifyOneStudentUseCase> {
        ModifyOneStudentUseCaseImp(get(), get())
    }

    single<ValidateVoiceStudentUseCase> {
        ValidateVoiceStudentUseCaseImp()
    }

    single<ValidateFieldsStudentUseCase> {
        ValidateFieldsStudentUseCaseImp()
    }

    viewModel {
        RegisterStudentViewModel(get(), get(), get(), get(), get())
    }
    viewModel {
        ListStudentViewModel(get(), get())
    }
}
