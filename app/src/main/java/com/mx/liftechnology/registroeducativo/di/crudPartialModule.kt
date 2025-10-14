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
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Koin module for partial-related CRUD dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val crudPartialModule = module {

    /**
     * Provides an instance of [RegisterListPartialApiCall].
     */
    factory { get<Retrofit>().create(RegisterListPartialApiCall::class.java) }

    /**
     * Provides an instance of [GetListPartialApiCall].
     */
    factory { get<Retrofit>().create(GetListPartialApiCall::class.java) }

    /**
     * Provides a singleton instance of [RegisterListPartialRepository].
     */
    single<RegisterListPartialRepository> {
        RegisterListPartialRepositoryImp(get())
    }

    /**
     * Provides a singleton instance of [GetListPartialRepository].
     */
    single<GetListPartialRepository> {
        GetListPartialRepositoryImp(get())
    }

    /**
     * Provides a singleton instance of [RegisterListPartialUseCase].
     */
    single { RegisterListPartialUseCase(get(), get()) }

    /**
     * Provides a singleton instance of [GetListPartialUseCase].
     */
    single { GetListPartialUseCase(get(), get()) }

    /**
     * Provides a singleton instance of [ValidateFieldsRegisterPartialUseCase].
     */
    single<ValidateFieldsRegisterPartialUseCase> {
        ValidateFieldsRegisterPartialUseCaseImp()
    }

    /**
     * Provides a singleton instance of [SavePartialUseCase].
     */
    single { SavePartialUseCase(get()) }

    /**
     * Provides a singleton instance of [UpdatePartialUseCase].
     */
    single { UpdatePartialUseCase(get()) }

    /**
     * Provides an instance of [RegisterPartialViewModel].
     */
    viewModel {
        RegisterPartialViewModel(get(), get(), get(), get())
    }
}
