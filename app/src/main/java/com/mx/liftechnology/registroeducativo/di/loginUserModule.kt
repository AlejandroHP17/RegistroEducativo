package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowLogin.LoginApiCall
import com.mx.liftechnology.data.repository.flowLogin.login.LoginRepository
import com.mx.liftechnology.data.repository.flowLogin.login.LoginRepositoryImp
import com.mx.liftechnology.domain.usecase.loginflowdomain.login.LoginUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginFlowUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginFlowUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.flowLogin.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
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
    single<LoginRepository> {
        LoginRepositoryImp(get())
    }

    /**
     * Provides a singleton instance of [LoginUseCase].
     */
    single {
        LoginUseCase(get(),get(), get())
    }

    /**
     * Provides a singleton instance of [ValidateFieldsLoginFlowUseCase].
     */
    single<ValidateFieldsLoginFlowUseCase> {
        ValidateFieldsLoginFlowUseCaseImp()
    }

    /**
     * Provides an instance of [LoginViewModel].
     */
    viewModel {
        LoginViewModel(get(), get(), get())
    }
}
