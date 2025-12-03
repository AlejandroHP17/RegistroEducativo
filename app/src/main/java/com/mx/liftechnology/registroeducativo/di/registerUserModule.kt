package com.mx.liftechnology.registroeducativo.di


import com.mx.liftechnology.data.repositoryImpl.auth.RegisterUserRepositoryImpl
import com.mx.liftechnology.domain.repository.auth.RegisterUserRepository
import com.mx.liftechnology.domain.usecase.auth.RegisterUserUseCase
import com.mx.liftechnology.registroeducativo.main.ui.auth.register.RegisterUserViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


/**
 * Koin module for user registration-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val registerUserModule = module {

    /**
     * Provides a singleton instance of [RegisterUserUseCase].
     */
    singleOf(::RegisterUserUseCase)

    /**
     * Provides an instance of [RegisterUserViewModel].
     */
    viewModelOf(::RegisterUserViewModel)
}
