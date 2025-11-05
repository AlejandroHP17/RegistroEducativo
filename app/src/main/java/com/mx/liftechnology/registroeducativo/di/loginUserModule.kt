package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowLogin.LoginApiCall
import com.mx.liftechnology.data.repository.flowLogin.login.LoginRepository
import com.mx.liftechnology.data.repository.flowLogin.login.LoginRepositoryImp
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginFlowUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginFlowUseCaseImp
import com.mx.liftechnology.domain.usecase.loginflowdomain.login.LoginUseCase
import com.mx.liftechnology.registroeducativo.main.ui.flowLogin.login.LoginViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Koin module for login-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val loginUserModule = module {

    /**
     * Provides an instance of [LoginApiCall].
     */
    factory { get<Retrofit>().create(LoginApiCall::class.java) }

    /**
     * Provides a singleton instance of [LoginRepository].
     */
    singleOf(::LoginRepositoryImp) {
        bind<LoginRepository>()
    }

    /**
     * Provides a singleton instance of [LoginUseCase].
     */
    singleOf(::LoginUseCase)

    /**
     * Provides a singleton instance of [ValidateFieldsLoginFlowUseCase].
     */
    singleOf(::ValidateFieldsLoginFlowUseCaseImp) {
        bind<ValidateFieldsLoginFlowUseCase>()
    }

    /**
     * Provides an instance of [LoginViewModel].
     */
    viewModelOf(::LoginViewModel)
}
