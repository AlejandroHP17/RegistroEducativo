package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.CCTApiCall
import com.mx.liftechnology.core.network.callapi.RegisterApiCall
import com.mx.liftechnology.data.repository.flowLogin.RegisterRepository
import com.mx.liftechnology.data.repository.flowLogin.RegisterRepositoryImp
import com.mx.liftechnology.domain.usecase.flowlogin.CCTUseCase
import com.mx.liftechnology.domain.usecase.flowlogin.RegisterUseCase
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI to register user and get CCT
 * @author pelkidev
 * @since 1.0.0
 */
val registerModule = module {

    factory { get<Retrofit>().create(CCTApiCall::class.java) }
    factory { get<Retrofit>().create(RegisterApiCall::class.java) }

    single <RegisterRepository>{
        RegisterRepositoryImp(get(), get ())
    }

    single {
        RegisterUseCase(get())
    }

    single {
        CCTUseCase(get())
    }

    viewModel {
        RegisterViewModel(get(), get())
    }
}
