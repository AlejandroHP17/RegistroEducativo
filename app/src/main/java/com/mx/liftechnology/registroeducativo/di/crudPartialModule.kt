package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.schoolCycle.GetListPartialApiCall
import com.mx.liftechnology.core.network.apiCall.schoolCycle.RegisterListPartialApiCall
import com.mx.liftechnology.data.repository.schoolCycle.partial.GetListPartialRepository
import com.mx.liftechnology.data.repository.schoolCycle.partial.GetListPartialRepositoryImpl
import com.mx.liftechnology.data.repository.schoolCycle.partial.RegisterListPartialRepository
import com.mx.liftechnology.data.repository.schoolCycle.partial.RegisterListPartialRepositoryImpl
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.RegisterListPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.SavePartialMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.UpdatePartialMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.ValidateFieldsRegisterPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.ValidateFieldsRegisterPartialUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.partial.RegisterPartialViewModel
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
    singleOf(::RegisterListPartialRepositoryImpl){
        bind<RegisterListPartialRepository>()
    }
    /**
     * Provides a singleton instance of [GetListPartialRepository].
     */
    singleOf(::GetListPartialRepositoryImpl){
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
     * Provides a singleton instance of [SavePartialMenuUseCase].
     */
    singleOf(::SavePartialMenuUseCase)

    /**
     * Provides a singleton instance of [UpdatePartialMenuUseCase].
     */
    singleOf(::UpdatePartialMenuUseCase)

    /**
     * Provides an instance of [RegisterPartialViewModel].
     */
    viewModelOf(::RegisterPartialViewModel)
}
