package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.RegisterApiCall
import com.mx.liftechnology.data.repository.loginflowdata.RegisterRepository
import com.mx.liftechnology.data.repository.loginflowdata.RegisterRepositoryImp
import com.mx.liftechnology.domain.usecase.loginflowdomain.RegisterUserUseCase
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register.RegisterUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val registerUserModule = module {

    factory { get<Retrofit>().create(RegisterApiCall::class.java) }

    single<RegisterRepository> {
        RegisterRepositoryImp(get())
    }

    single {
        RegisterUserUseCase(get())
    }

    viewModel {
        RegisterUserViewModel(get(), get(), get())
    }
}
