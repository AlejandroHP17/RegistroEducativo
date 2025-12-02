package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.data.repositoryImpl.auth.RegisterUserRepositoryImpl
import com.mx.liftechnology.domain.repository.auth.RegisterUserRepository
import com.mx.liftechnology.domain.usecase.auth.RegisterUserUseCase
import com.mx.liftechnology.registroeducativo.main.ui.auth.register.RegisterUserViewModel
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
     * Provides an instance of [AuthApi].
     */
    factory { get<Retrofit>().create(AuthApi::class.java) }

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
