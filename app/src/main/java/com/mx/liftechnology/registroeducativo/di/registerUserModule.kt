package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowLogin.RegisterUserApiCall
import com.mx.liftechnology.data.repository.flowLogin.register.RegisterUserRepository
import com.mx.liftechnology.data.repository.flowLogin.register.RegisterUserRepositoryImpl
import com.mx.liftechnology.domain.usecase.loginflowdomain.register.RegisterUserUseCase
import com.mx.liftechnology.registroeducativo.main.ui.flowLogin.register.RegisterUserViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
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
    singleOf(::RegisterUserRepositoryImpl){
        bind<RegisterUserRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterUserUseCase].
     */
    singleOf(::RegisterUserUseCase)

    /**
     * Provides an instance of [RegisterUserViewModel].
     */
    viewModelOf(::RegisterUserViewModel)
}
