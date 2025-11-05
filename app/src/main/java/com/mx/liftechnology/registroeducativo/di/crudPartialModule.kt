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
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
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
    singleOf(::RegisterListPartialRepositoryImp){
        bind<RegisterListPartialRepository>()
    }
    /**
     * Provides a singleton instance of [GetListPartialRepository].
     */
    singleOf(::GetListPartialRepositoryImp){
        bind<GetListPartialRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterListPartialUseCase].
     */
    singleOf(::RegisterListPartialUseCase)

    /**
     * Provides a singleton instance of [GetListPartialUseCase].
     */
    singleOf(::GetListPartialUseCase)

    /**
     * Provides a singleton instance of [ValidateFieldsRegisterPartialUseCase].
     */
    singleOf(::ValidateFieldsRegisterPartialUseCaseImp) {
        bind<ValidateFieldsRegisterPartialUseCase>()
    }

    /**
     * Provides a singleton instance of [SavePartialUseCase].
     */
    singleOf(::SavePartialUseCase)

    /**
     * Provides a singleton instance of [UpdatePartialUseCase].
     */
    singleOf(::UpdatePartialUseCase)

    /**
     * Provides an instance of [RegisterPartialViewModel].
     */
    viewModelOf(::RegisterPartialViewModel)
}
