package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowLogin.RegisterUserApiCall
import com.mx.liftechnology.data.repository.flowLogin.register.RegisterUserRepository
import com.mx.liftechnology.data.repository.flowLogin.register.RegisterUserRepositoryImp
import com.mx.liftechnology.domain.usecase.loginflowdomain.register.RegisterUserUseCase
import com.mx.liftechnology.registroeducativo.main.ui.flowLogin.register.RegisterUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Koin module for user registration-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val registerUserModule = module {

    /**
     * Provides an instance of [RegisterUserApiCall].
     */
    factory { get<Retrofit>().create(RegisterUserApiCall::class.java) }

    /**
     * Provides a singleton instance of [RegisterUserRepository].
     */
    single<RegisterUserRepository> {
        RegisterUserRepositoryImp(get())
    }

    /**
     * Provides a singleton instance of [RegisterUserUseCase].
     */
    single {
        RegisterUserUseCase(get())
    }

    /**
     * Provides an instance of [RegisterUserViewModel].
     */
    viewModel {
        RegisterUserViewModel(get(), get(), get())
    }
}
