package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.RegisterPartialApiCall
import com.mx.liftechnology.data.repository.registerFlow.RegisterPartialRepository
import com.mx.liftechnology.data.repository.registerFlow.RegisterPartialRepositoryImp
import com.mx.liftechnology.domain.usecase.flowregisterdata.RegisterPartialUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.RegisterPartialUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.partial.RegisterPartialViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI to register user and get CCT
 * @author pelkidev
 * @since 1.0.0
 */
val registerPartialModule = module {

    factory { get<Retrofit>().create(RegisterPartialApiCall::class.java) }


    single <RegisterPartialRepository>{
        RegisterPartialRepositoryImp(get())
    }

    single <RegisterPartialUseCase>{
        RegisterPartialUseCaseImp(get(),get())
    }

    viewModel {
        RegisterPartialViewModel(get(), get(), get())
    }
}
