package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.RegisterApiCall
import com.mx.liftechnology.data.repository.loginflowdata.RegisterRepository
import com.mx.liftechnology.data.repository.loginflowdata.RegisterRepositoryImp
import com.mx.liftechnology.domain.usecase.loginflowdomain.RegisterUseCase
import com.mx.liftechnology.domain.usecase.loginflowdomain.RegisterUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val registerModule = module {

    factory { get<Retrofit>().create(RegisterApiCall::class.java) }

    single<RegisterRepository> {
        RegisterRepositoryImp(get())
    }

    single<RegisterUseCase> {
        RegisterUseCaseImp(get())
    }

    viewModel {
        RegisterViewModel(get(), get(), get())
    }
}
