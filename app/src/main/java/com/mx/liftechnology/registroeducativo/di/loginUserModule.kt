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
 * @author pelkidev
 * @since 1.0.0
 */
val loginUserModule = module {

    factory { get<Retrofit>().create(LoginApiCall::class.java) }

    single<LoginRepository> {
        LoginRepositoryImp(get())
    }

    single {
        LoginUseCase(get(),get(), get())
    }

    single<ValidateFieldsLoginFlowUseCase> {
        ValidateFieldsLoginFlowUseCaseImp()
    }

    viewModel {
        LoginViewModel(get(), get(), get())
    }
}
