package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListPartialApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterListPartialApiCall
import com.mx.liftechnology.data.repository.flowMain.partial.GetListPartialRepository
import com.mx.liftechnology.data.repository.flowMain.partial.GetListPartialRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.partial.RegisterListPartialRepository
import com.mx.liftechnology.data.repository.flowMain.partial.RegisterListPartialRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.RegisterListPartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.SavePartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.UpdatePartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.ValidateFieldsRegisterPartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.ValidateFieldsRegisterPartialUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.partial.RegisterPartialViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val crudPartialModule = module {

    factory { get<Retrofit>().create(RegisterListPartialApiCall::class.java) }
    factory { get<Retrofit>().create(GetListPartialApiCall::class.java) }

    single<RegisterListPartialRepository> {
        RegisterListPartialRepositoryImp(get())
    }
    single<GetListPartialRepository> {
        GetListPartialRepositoryImp(get())
    }

    single { RegisterListPartialUseCase(get(), get()) }
    single { GetListPartialUseCase(get(), get()) }

    single<ValidateFieldsRegisterPartialUseCase> {
        ValidateFieldsRegisterPartialUseCaseImp()
    }
    single { SavePartialUseCase(get()) }
    single { UpdatePartialUseCase(get()) }

    viewModel {
        RegisterPartialViewModel(get(), get(), get(), get())
    }
}
