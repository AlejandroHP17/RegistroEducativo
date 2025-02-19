package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.GetPartialApiCall
import com.mx.liftechnology.core.network.callapi.RegisterPartialApiCall
import com.mx.liftechnology.data.repository.registerFlow.CrudPartialRepository
import com.mx.liftechnology.data.repository.registerFlow.CrudPartialRepositoryImp
import com.mx.liftechnology.domain.usecase.flowdata.partial.CreatePartialUseCase
import com.mx.liftechnology.domain.usecase.flowdata.partial.CreatePartialUseCaseImp
import com.mx.liftechnology.domain.usecase.flowdata.partial.ReadPartialUseCase
import com.mx.liftechnology.domain.usecase.flowdata.partial.ReadPartialUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.partial.RegisterPartialViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI to register user and get CCT
 * @author pelkidev
 * @since 1.0.0
 */
val crudPartialModule = module {

    factory { get<Retrofit>().create(RegisterPartialApiCall::class.java) }
    factory { get<Retrofit>().create(GetPartialApiCall::class.java) }

    single<CrudPartialRepository> {
        CrudPartialRepositoryImp(get(), get())
    }

    single<CreatePartialUseCase> {
        CreatePartialUseCaseImp(get(), get())
    }
    single<ReadPartialUseCase> {
        ReadPartialUseCaseImp(get(), get())
    }

    viewModel {
        RegisterPartialViewModel(get(), get(), get(), get())
    }
}
