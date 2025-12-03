package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.share.ValidateAuthFieldsUseCase
import com.mx.liftechnology.domain.usecase.share.ValidateAuthFieldsUseCaseImp
import com.mx.liftechnology.domain.usecase.share.GetListFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.share.SaveFormativeFieldIdSelectedUseCase
import com.mx.liftechnology.domain.usecase.share.ValidateFieldsRegisterPartialUseCase
import com.mx.liftechnology.domain.usecase.share.ValidateFieldsRegisterPartialUseCaseImp
import com.mx.liftechnology.domain.usecase.share.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.share.ValidateFieldsStudentUseCase
import com.mx.liftechnology.domain.usecase.share.ValidateFieldsStudentUseCaseImp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val shareDomainModule = module {
    /**
     * Proporciona una instancia singleton de [ValidateAuthFieldsUseCase].
     */
    singleOf(::ValidateAuthFieldsUseCaseImp) {
        bind<ValidateAuthFieldsUseCase>()
    }

    /**
     * Proporciona una instancia singleton de [GetListStudentUseCase].
     * Proporciona una instancia singleton de [ValidateFieldsStudentUseCase].
     */
    singleOf(::GetListStudentUseCase)
    singleOf(::ValidateFieldsStudentUseCaseImp) {
        bind<ValidateFieldsStudentUseCase>()
    }

    /**
     * Proporciona una instancia singleton de [SaveFormativeFieldIdSelectedUseCase].
     */
    singleOf(::SaveFormativeFieldIdSelectedUseCase)

    /**
     * Proporciona una instancia singleton de [GetListFormativeFieldUseCase].
     */
    singleOf(::GetListFormativeFieldUseCase)


    /**
     * Proporciona una instancia singleton de [ValidateFieldsRegisterPartialUseCase].
     */
    singleOf(::ValidateFieldsRegisterPartialUseCaseImp) {
        bind<ValidateFieldsRegisterPartialUseCase>()
    }
}