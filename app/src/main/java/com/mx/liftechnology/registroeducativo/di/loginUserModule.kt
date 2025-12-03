package com.mx.liftechnology.registroeducativo.di

/**
 * @file Define el módulo de Koin para dependencias relacionadas con el inicio de sesión.
 * @author Pelkidev
 * @version 1.0.0
 */

import com.mx.liftechnology.domain.usecase.auth.ValidateLoginFieldsUseCase
import com.mx.liftechnology.domain.usecase.auth.ValidateLoginFieldsUseCaseImp
import com.mx.liftechnology.domain.usecase.auth.GetDataUserUseCase
import com.mx.liftechnology.domain.usecase.auth.LoginUseCase
import com.mx.liftechnology.registroeducativo.main.ui.auth.login.LoginViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


/**
 * Módulo de Koin para dependencias relacionadas con el inicio de sesión.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val loginUserModule = module {


    /**
     * Proporciona una instancia singleton de [LoginUseCase].
     */
    singleOf(::LoginUseCase)

    /**
     * Proporciona una instancia singleton de [GetDataUserUseCase].
     */
    singleOf(::GetDataUserUseCase)

    /**
     * Proporciona una instancia singleton de [ValidateLoginFieldsUseCase].
     */
    singleOf(::ValidateLoginFieldsUseCaseImp) {
        bind<ValidateLoginFieldsUseCase>()
    }

    /**
     * Proporciona una instancia de [LoginViewModel].
     */
    viewModelOf(::LoginViewModel)
}
