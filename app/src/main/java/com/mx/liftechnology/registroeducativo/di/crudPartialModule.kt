package com.mx.liftechnology.registroeducativo.di


import com.mx.liftechnology.data.repositoryImpl.partial.GetListPartialRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.partial.RegisterListPartialRepositoryImpl
import com.mx.liftechnology.domain.repository.schoolCycle.partial.GetListPartialRepository
import com.mx.liftechnology.domain.repository.schoolCycle.partial.RegisterListPartialRepository
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


/**
 * Koin module for partial-related CRUD dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val crudPartialModule = module {




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
