/**
 * @file Define el módulo de Koin para dependencias compartidas en la aplicación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.principal.SharedViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Módulo de Koin para dependencias compartidas en toda la aplicación.
 * 
 * Este módulo proporciona instancias de ViewModels y otros componentes
 * que se comparten entre múltiples pantallas o que gestionan estado global.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val sharedModule = module {

    /**
     * Proporciona una instancia de [SharedViewModel].
     * 
     * El SharedViewModel se utiliza para:
     * - Gestionar el estado de sesión (expiración, cierre de sesión)
     * - Mostrar toasts globales
     * - Compartir datos entre pantallas
     */
    viewModelOf(::SharedViewModel)
}
