package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.schoolCycle.menu.GetControlMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.GetControlRegisterUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.GetGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.GetListPartialMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.SavePartialMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.UpdateGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.menu.UpdatePartialMenuUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.RegisterListPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.RegisterPartialWithValidationUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.GetCctUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.RegisterCycleSchoolUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.RegisterSchoolWithValidationUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.ValidateFieldsRegisterSchoolUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.ValidateFieldsRegisterSchoolUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.menu.MenuViewModel
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.partial.RegisterPartialViewModel
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.school.RegisterSchoolViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val schoolCycleModule = module{

    /**
     * Proporciona una instancia singleton de [GetGroupMenuUseCase].
     * Proporciona una instancia singleton de [UpdateGroupMenuUseCase].
     * Proporciona una instancia singleton de [GetControlMenuUseCase].
     * Proporciona una instancia singleton de [GetControlRegisterUseCase].
     * Proporciona una instancia singleton de [GetListPartialMenuUseCase].
     * Proporciona una instancia singleton de [MenuViewModel].
     * Proporciona una instancia singleton de [UpdatePartialMenuUseCase].
     */
    singleOf(::GetGroupMenuUseCase)
    singleOf(::UpdateGroupMenuUseCase)
    singleOf(::GetControlMenuUseCase)
    singleOf(::GetControlRegisterUseCase)
    singleOf(::GetListPartialMenuUseCase)
    singleOf(::SavePartialMenuUseCase)
    singleOf(::UpdatePartialMenuUseCase)

    /**
     * Proporciona una instancia de [MenuViewModel].
     */
    viewModelOf(::MenuViewModel)


    /**
     * Proporciona una instancia singleton de [RegisterPartialWithValidationUseCase].
     * Proporciona una instancia singleton de [RegisterListPartialUseCase].
     * Proporciona una instancia singleton de [GetListPartialUseCase].
     */
    singleOf(::RegisterPartialWithValidationUseCase)
    singleOf(::RegisterListPartialUseCase)
    singleOf(::GetListPartialUseCase)

    /**
     * Proporciona una instancia de [RegisterPartialViewModel].
     */
    viewModelOf(::RegisterPartialViewModel)


    /**
     * Proporciona una instancia de [GetCctUseCase].
     * Proporciona una instancia singleton de [RegisterSchoolWithValidationUseCase].
     * Proporciona una instancia singleton de [RegisterCycleSchoolUseCase].
     * Proporciona una instancia singleton de [ValidateFieldsRegisterSchoolUseCase].
     */
    factory{GetCctUseCase(get()) }
    singleOf(::RegisterSchoolWithValidationUseCase)
    singleOf(::RegisterCycleSchoolUseCase)
    singleOf(::ValidateFieldsRegisterSchoolUseCaseImp){
        bind<ValidateFieldsRegisterSchoolUseCase>()
    }

    /**
     * Proporciona una instancia de [RegisterSchoolViewModel].
     */
    viewModelOf(::RegisterSchoolViewModel)
}