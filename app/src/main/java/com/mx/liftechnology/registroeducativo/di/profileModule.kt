package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Módulo de Koin para dependencias relacionadas con el perfil de usuario.
 * 
 * Este módulo proporciona las instancias necesarias para:
 * - Gestión del perfil del usuario autenticado
 * - Visualización y edición de datos del perfil
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val profileModule = module {
    /**
     * Proporciona una instancia de [ProfileViewModel].
     * ViewModel para la pantalla de perfil de usuario.
     */
    viewModelOf(::ProfileViewModel)
}
