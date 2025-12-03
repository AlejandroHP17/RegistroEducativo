package com.mx.liftechnology.registroeducativo.di
/**
 * @file Define el módulo de Koin para dependencias relacionadas con el registro de usuario.
 * @author Pelkidev
 * @version 1.0.0
 */

import com.mx.liftechnology.domain.usecase.auth.RegisterUserUseCase
import com.mx.liftechnology.registroeducativo.main.ui.auth.register.RegisterUserViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module





/**
 * Módulo de Koin para dependencias relacionadas con el registro de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val registerUserModule = module {

    /**
     * Proporciona una instancia singleton de [RegisterUserUseCase].
     */
    singleOf(::RegisterUserUseCase)

    /**
     * Proporciona una instancia de [RegisterUserViewModel].
     */
    viewModelOf(::RegisterUserViewModel)
}
