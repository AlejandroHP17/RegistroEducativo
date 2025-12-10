package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.partial.RegisterListPartialUseCase
import com.mx.liftechnology.domain.usecase.partial.RegisterPartialWithValidationUseCase
import com.mx.liftechnology.registroeducativo.main.ui.partial.RegisterPartialViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Módulo de Koin para dependencias relacionadas con la gestión de parciales.
 * 
 * Este módulo proporciona las instancias necesarias para:
 * - Registro de parciales con validación
 * - Registro de lista de parciales
 * - Obtención de lista de parciales
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val partialModule = module {

    /**
     * Proporciona una instancia singleton de [RegisterPartialWithValidationUseCase].
     * Caso de uso para registrar un parcial con validación de campos.
     */
    singleOf(::RegisterPartialWithValidationUseCase)
    
    /**
     * Proporciona una instancia singleton de [RegisterListPartialUseCase].
     * Caso de uso para registrar una lista de parciales.
     */
    singleOf(::RegisterListPartialUseCase)
    
    /**
     * Proporciona una instancia singleton de [GetListPartialUseCase].
     * Caso de uso para obtener la lista de parciales.
     */
    singleOf(::GetListPartialUseCase)

    /**
     * Proporciona una instancia de [RegisterPartialViewModel].
     */
    viewModelOf(::RegisterPartialViewModel)
}