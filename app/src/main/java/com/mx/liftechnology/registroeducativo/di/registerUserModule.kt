package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowLogin.RegisterUserApiCall
import com.mx.liftechnology.data.repository.flowLogin.register.RegisterUserRepository
import com.mx.liftechnology.data.repository.flowLogin.register.RegisterUserRepositoryImp
import com.mx.liftechnology.domain.usecase.loginflowdomain.register.RegisterUserUseCase
import com.mx.liftechnology.registroeducativo.main.ui.flowLogin.register.RegisterUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val registerUserModule = module {

    factory { get<Retrofit>().create(RegisterUserApiCall::class.java) }

    single<RegisterUserRepository> {
        RegisterUserRepositoryImp(get())
    }

    single {
        RegisterUserUseCase(get())
    }

    viewModel {
        RegisterUserViewModel(get(), get(), get())
    }
}
