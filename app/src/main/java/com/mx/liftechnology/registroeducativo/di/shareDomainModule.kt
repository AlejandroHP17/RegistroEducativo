package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.auth.ValidateAuthFieldsUseCase
import com.mx.liftechnology.domain.usecase.auth.ValidateAuthFieldsUseCaseImp
import com.mx.liftechnology.domain.usecase.formativeField.GetListFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.formativeField.SaveFormativeFieldIdSelectedUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.ValidateFieldsRegisterPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.ValidateFieldsRegisterPartialUseCaseImp
import com.mx.liftechnology.domain.usecase.student.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.student.ValidateFieldsStudentUseCase
import com.mx.liftechnology.domain.usecase.student.ValidateFieldsStudentUseCaseImp
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