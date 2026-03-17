package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.formativeField.DeleteFormativeFieldsUseCase
import com.mx.liftechnology.domain.usecase.formativeField.GetListWorkTypeUseCase
import com.mx.liftechnology.domain.usecase.formativeField.RegisterFormativeFieldsBulkUseCase
import com.mx.liftechnology.domain.usecase.formativeField.RegisterFormativeFieldsWithValidationUseCase
import com.mx.liftechnology.domain.usecase.formativeField.ValidateFieldsFormativeFieldsUseCase
import com.mx.liftechnology.domain.usecase.formativeField.ValidateFieldsFormativeFieldsUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.formativeFields.list.ListFormativeFieldsViewModel
import com.mx.liftechnology.registroeducativo.main.ui.formativeFields.register.RegisterFormativeFieldsViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Módulo de Koin para dependencias CRUD relacionadas con campos formativos (materias).
 * 
 * Este módulo proporciona las instancias necesarias para:
 * - Validación de campos de campos formativos
 * - Registro de campos formativos con validación
 * - Registro masivo de campos formativos
 * - Eliminación de campos formativos
 * - Obtención de tipos de trabajo
 * - Gestión de listas y registro de campos formativos
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val formativeFieldModule = module {

    /**
     * Proporciona una instancia singleton de [ValidateFieldsFormativeFieldsUseCase].
     * Caso de uso para validar los campos del formulario de campo formativo.
     */
    singleOf(::ValidateFieldsFormativeFieldsUseCaseImp) {
        bind<ValidateFieldsFormativeFieldsUseCase>()
    }

    /**
     * Proporciona una instancia singleton de [RegisterFormativeFieldsWithValidationUseCase].
     * Caso de uso para registrar un campo formativo con validación de campos.
     */
    singleOf(::RegisterFormativeFieldsWithValidationUseCase)
    
    /**
     * Proporciona una instancia singleton de [GetListWorkTypeUseCase].
     * Caso de uso para obtener la lista de tipos de trabajo.
     */
    singleOf(::GetListWorkTypeUseCase)
    
    /**
     * Proporciona una instancia singleton de [RegisterFormativeFieldsBulkUseCase].
     * Caso de uso para registrar múltiples campos formativos de forma masiva.
     */
    singleOf(::RegisterFormativeFieldsBulkUseCase)

    /**
     * Proporciona una instancia singleton de [DeleteFormativeFieldsUseCase].
     * Caso de uso para eliminar un campo formativo.
     */
    singleOf(::DeleteFormativeFieldsUseCase)

    /**
     * Proporciona una instancia de [RegisterFormativeFieldsViewModel].
     * ViewModel para la pantalla de registro de campos formativos.
     */
    viewModelOf(::RegisterFormativeFieldsViewModel)
    
    /**
     * Proporciona una instancia de [ListFormativeFieldsViewModel].
     * ViewModel para la pantalla de lista de campos formativos.
     */
    viewModelOf(::ListFormativeFieldsViewModel)
}
