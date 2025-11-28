package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.auth.LoginWithValidationUseCase
import com.mx.liftechnology.domain.usecase.auth.RegisterUserWithValidationUseCase
import com.mx.liftechnology.domain.usecase.evaluation.RegisterEvaluationWithValidationUseCase
import com.mx.liftechnology.domain.usecase.formativeField.RegisterFormativeFieldsWithValidationUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.RegisterPartialWithValidationUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.RegisterSchoolWithValidationUseCase
import com.mx.liftechnology.domain.usecase.student.EditStudentWithValidationUseCase
import com.mx.liftechnology.domain.usecase.student.RegisterStudentWithValidationUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val validationModule = module{
    singleOf(::LoginWithValidationUseCase)
    singleOf(::RegisterUserWithValidationUseCase)
    singleOf(::RegisterSchoolWithValidationUseCase)
    singleOf(::RegisterEvaluationWithValidationUseCase)
    singleOf(::RegisterFormativeFieldsWithValidationUseCase)
    singleOf(::RegisterPartialWithValidationUseCase)
    singleOf(::RegisterSchoolWithValidationUseCase)
    singleOf(::EditStudentWithValidationUseCase)
    singleOf(::RegisterStudentWithValidationUseCase)
}