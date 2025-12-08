package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.auth.GetDataUserUseCase
import com.mx.liftechnology.domain.usecase.auth.LoginUseCase
import com.mx.liftechnology.domain.usecase.auth.LoginWithValidationUseCase
import com.mx.liftechnology.domain.usecase.auth.RegisterUserUseCase
import com.mx.liftechnology.domain.usecase.auth.RegisterUserWithValidationUseCase
import com.mx.liftechnology.registroeducativo.main.ui.auth.forgetPassword.ForgetPasswordViewModel
import com.mx.liftechnology.registroeducativo.main.ui.auth.login.LoginViewModel
import com.mx.liftechnology.registroeducativo.main.ui.auth.register.RegisterUserViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Módulo de Koin para las dependencias relacionadas con la funcionalidad de auth.
 * Este módulo se encarga de proveer las instancias necesarias para la pantalla de recuperación de contraseña,
 * como el [ForgetPasswordViewModel].
 * Este módulo se encarga de proveer las instancias necesarias para la pantalla de iniciar sesion,
 * como el [LoginViewModel].
 * Este módulo se encarga de proveer las instancias necesarias para la pantalla registro de usuario,
 * como el [RegisterUserViewModel].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val authModule = module{

    /**
     * Provee una instancia de [ForgetPasswordViewModel].
     */
    viewModelOf(::ForgetPasswordViewModel)


    /**
     * Proporciona una instancia singleton de [LoginUseCase].
     * Proporciona una instancia singleton de [GetDataUserUseCase].
     * Proporciona una instancia singleton de [LoginWithValidationUseCase].
     */
    singleOf(::LoginUseCase)
    singleOf(::GetDataUserUseCase)
    singleOf(::LoginWithValidationUseCase)

    /**
     * Proporciona una instancia de [LoginViewModel].
     */
    viewModelOf(::LoginViewModel)


    /**
     * Proporciona una instancia singleton de [RegisterUserUseCase].
     * Proporciona una instancia singleton de [RegisterUserWithValidationUseCase].
     */
    singleOf(::RegisterUserUseCase)
    singleOf(::RegisterUserWithValidationUseCase)

    /**
     * Proporciona una instancia de [RegisterUserViewModel].
     */
    viewModelOf(::RegisterUserViewModel)
}