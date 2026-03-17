package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.splash.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Módulo de Koin para dependencias relacionadas con la pantalla de inicio (splash).
 * 
 * Este módulo proporciona las instancias necesarias para:
 * - Gestión del estado de la pantalla de inicio
 * - Verificación de sesión de usuario
 * - Navegación inicial de la aplicación
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val splashModule = module {
    /**
     * Proporciona una instancia de [SplashViewModel].
     * ViewModel para la pantalla de inicio de la aplicación.
     */
    viewModelOf(::SplashViewModel)
}
