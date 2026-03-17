package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.menu.GetControlMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.GetControlRegisterUseCase
import com.mx.liftechnology.domain.usecase.menu.GetGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.GetListPartialMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.SavePartialMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.UpdateGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.UpdatePartialMenuUseCase
import com.mx.liftechnology.registroeducativo.main.ui.menu.MenuViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Módulo de Koin para dependencias relacionadas con el menú principal.
 * 
 * Este módulo proporciona las instancias necesarias para:
 * - Gestión de grupos del menú
 * - Control de opciones del menú
 * - Gestión de parciales en el menú
 * - Actualización de grupos y parciales
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val menuModule = module {
    /**
     * Proporciona una instancia singleton de [GetGroupMenuUseCase].
     * Caso de uso para obtener los grupos del menú.
     */
    singleOf(::GetGroupMenuUseCase)
    
    /**
     * Proporciona una instancia singleton de [UpdateGroupMenuUseCase].
     * Caso de uso para actualizar un grupo del menú.
     */
    singleOf(::UpdateGroupMenuUseCase)
    
    /**
     * Proporciona una instancia singleton de [GetControlMenuUseCase].
     * Caso de uso para obtener el control del menú.
     */
    singleOf(::GetControlMenuUseCase)
    
    /**
     * Proporciona una instancia singleton de [GetControlRegisterUseCase].
     * Caso de uso para obtener el control de registro del menú.
     */
    singleOf(::GetControlRegisterUseCase)
    
    /**
     * Proporciona una instancia singleton de [GetListPartialMenuUseCase].
     * Caso de uso para obtener la lista de parciales del menú.
     */
    singleOf(::GetListPartialMenuUseCase)
    
    /**
     * Proporciona una instancia singleton de [SavePartialMenuUseCase].
     * Caso de uso para guardar un parcial en el menú.
     */
    singleOf(::SavePartialMenuUseCase)
    
    /**
     * Proporciona una instancia singleton de [UpdatePartialMenuUseCase].
     * Caso de uso para actualizar un parcial en el menú.
     */
    singleOf(::UpdatePartialMenuUseCase)

    /**
     * Proporciona una instancia de [MenuViewModel].
     */
    viewModelOf(::MenuViewModel)
}