/**
 * @file Contiene el módulo de Koin para las dependencias de la pantalla de olvido de contraseña.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.flowLogin.forgetPassword.ForgetPasswordViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Módulo de Koin para las dependencias relacionadas con la funcionalidad de "olvidar contraseña".
 * Este módulo se encarga de proveer las instancias necesarias para la pantalla de recuperación de contraseña,
 * como el [ForgetPasswordViewModel].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val forgetPasswordModule = module {
    /**
     * Provee una instancia de [ForgetPasswordViewModel].
     */
    viewModelOf(::ForgetPasswordViewModel)
}
