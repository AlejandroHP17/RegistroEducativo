package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.RegisterStudentApiCall
import com.mx.liftechnology.data.repository.registerFlow.RegisterStudentRepository
import com.mx.liftechnology.data.repository.registerFlow.RegisterStudentRepositoryImp
import com.mx.liftechnology.domain.usecase.flowregisterdata.RegisterStudentUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.RegisterStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.flowregisterdata.ValidateFieldsStudentUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.ValidateFieldsStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.flowregisterdata.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.ValidateVoiceStudentUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.student.RegisterStudentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val registerStudentModule = module {

    factory { get<Retrofit>().create(RegisterStudentApiCall::class.java) }

    single <RegisterStudentRepository>{
        RegisterStudentRepositoryImp(get())
    }
    single <RegisterStudentUseCase>{
        RegisterStudentUseCaseImp(get(), get())
    }
    single <ValidateVoiceStudentUseCase>{
        ValidateVoiceStudentUseCaseImp()
    }

    single <ValidateFieldsStudentUseCase>{
        ValidateFieldsStudentUseCaseImp()
    }

    viewModel {
        RegisterStudentViewModel(get(),get(),get(),get())
    }
}
