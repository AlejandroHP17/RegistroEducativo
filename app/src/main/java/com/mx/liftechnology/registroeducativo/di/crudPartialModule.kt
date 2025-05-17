package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.GetListPartialApiCall
import com.mx.liftechnology.core.network.callapi.RegisterListPartialApiCall
import com.mx.liftechnology.data.repository.mainflowdata.partial.CrudPartialRepository
import com.mx.liftechnology.data.repository.mainflowdata.partial.CrudPartialRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.GetListPartialUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.RegisterListPartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.RegisterListPartialUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.SavePartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.UpdatePartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.ValidateFieldsRegisterPartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.ValidateFieldsRegisterPartialUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.partial.RegisterPartialViewModel
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

    single<CrudPartialRepository> {
        CrudPartialRepositoryImp(get(), get())
    }

    single<RegisterListPartialUseCase> {
        RegisterListPartialUseCaseImp(get(), get())
    }
    single<GetListPartialUseCase> {
        GetListPartialUseCaseImp(get(), get())
    }
    single<ValidateFieldsRegisterPartialUseCase> {
        ValidateFieldsRegisterPartialUseCaseImp()
    }
    single {
        SavePartialUseCase(get())
    }
    single {
        UpdatePartialUseCase(get())
    }

    viewModel {
        RegisterPartialViewModel(get(), get(), get(), get())
    }
}
