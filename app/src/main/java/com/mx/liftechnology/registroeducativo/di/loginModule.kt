package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.LoginApiCall
import com.mx.liftechnology.data.repository.loginflowdata.LoginRepository
import com.mx.liftechnology.data.repository.loginflowdata.LoginRepositoryImp
import com.mx.liftechnology.domain.usecase.loginflowdomain.LoginUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.LoginUseCaseImp
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.ValidateFieldsLoginUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * @author pelkidev
 * @since 1.0.0
 */
val loginModule = module {

    factory { get<Retrofit>().create(LoginApiCall::class.java) }

    single<LoginRepository> {
        LoginRepositoryImp(get())
    }

    single<LoginUseCase> {
        LoginUseCaseImp(get(), get(), get())
    }

    single<ValidateFieldsLoginUseCase> {
        ValidateFieldsLoginUseCaseImp()
    }

    viewModel {
        LoginViewModel(get(), get(), get())
    }
}
