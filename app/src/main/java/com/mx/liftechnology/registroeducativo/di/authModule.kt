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
 * Módulo de Koin para las dependencias relacionadas con la funcionalidad de autenticación.
 * 
 * Este módulo proporciona las instancias necesarias para:
 * - Pantalla de recuperación de contraseña: [ForgetPasswordViewModel]
 * - Pantalla de inicio de sesión: [LoginViewModel] y casos de uso relacionados
 * - Pantalla de registro de usuario: [RegisterUserViewModel] y casos de uso relacionados
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val authModule = module{

    /**
     * Proporciona una instancia de [ForgetPasswordViewModel].
     * ViewModel para la pantalla de recuperación de contraseña.
     */
    viewModelOf(::ForgetPasswordViewModel)

    /**
     * Proporciona una instancia singleton de [LoginUseCase].
     * Caso de uso para realizar el proceso de inicio de sesión.
     */
    singleOf(::LoginUseCase)
    
    /**
     * Proporciona una instancia singleton de [GetDataUserUseCase].
     * Caso de uso para obtener los datos del usuario autenticado.
     */
    singleOf(::GetDataUserUseCase)
    
    /**
     * Proporciona una instancia singleton de [LoginWithValidationUseCase].
     * Caso de uso para realizar el inicio de sesión con validación de campos.
     */
    singleOf(::LoginWithValidationUseCase)

    /**
     * Proporciona una instancia de [LoginViewModel].
     * ViewModel para la pantalla de inicio de sesión.
     */
    viewModelOf(::LoginViewModel)

    /**
     * Proporciona una instancia singleton de [RegisterUserUseCase].
     * Caso de uso para registrar un nuevo usuario.
     */
    singleOf(::RegisterUserUseCase)
    
    /**
     * Proporciona una instancia singleton de [RegisterUserWithValidationUseCase].
     * Caso de uso para registrar un nuevo usuario con validación de campos.
     */
    singleOf(::RegisterUserWithValidationUseCase)

    /**
     * Proporciona una instancia de [RegisterUserViewModel].
     * ViewModel para la pantalla de registro de usuario.
     */
    viewModelOf(::RegisterUserViewModel)
}