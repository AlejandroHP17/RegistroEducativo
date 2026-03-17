package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.domain.usecase.share.ValidateAuthFieldsUseCase
import com.mx.liftechnology.domain.usecase.share.ValidateAuthFieldsUseCaseImp
import com.mx.liftechnology.domain.usecase.share.GetListFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.share.SaveFormativeFieldIdSelectedUseCase
import com.mx.liftechnology.domain.usecase.share.ValidateFieldsRegisterPartialUseCase
import com.mx.liftechnology.domain.usecase.share.ValidateFieldsRegisterPartialUseCaseImp
import com.mx.liftechnology.domain.usecase.share.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.share.ValidateFieldsStudentUseCase
import com.mx.liftechnology.domain.usecase.share.ValidateFieldsStudentUseCaseImp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Módulo de Koin para dependencias compartidas del dominio.
 * 
 * Este módulo proporciona casos de uso que son utilizados por múltiples módulos:
 * - Validación de campos de autenticación
 * - Validación de campos de estudiantes
 * - Validación de campos de parciales
 * - Obtención de listas compartidas (estudiantes, campos formativos)
 * - Gestión de selección de campos formativos
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val shareDomainModule = module {
    /**
     * Proporciona una instancia singleton de [ValidateAuthFieldsUseCase].
     * Caso de uso para validar los campos del formulario de autenticación.
     */
    singleOf(::ValidateAuthFieldsUseCaseImp) {
        bind<ValidateAuthFieldsUseCase>()
    }

    /**
     * Proporciona una instancia singleton de [GetListStudentUseCase].
     * Caso de uso para obtener la lista de estudiantes.
     */
    singleOf(::GetListStudentUseCase)
    
    /**
     * Proporciona una instancia singleton de [ValidateFieldsStudentUseCase].
     * Caso de uso para validar los campos del formulario de estudiante.
     */
    singleOf(::ValidateFieldsStudentUseCaseImp) {
        bind<ValidateFieldsStudentUseCase>()
    }

    /**
     * Proporciona una instancia singleton de [SaveFormativeFieldIdSelectedUseCase].
     * Caso de uso para guardar el ID del campo formativo seleccionado.
     */
    singleOf(::SaveFormativeFieldIdSelectedUseCase)

    /**
     * Proporciona una instancia singleton de [GetListFormativeFieldUseCase].
     * Caso de uso para obtener la lista de campos formativos.
     */
    singleOf(::GetListFormativeFieldUseCase)

    /**
     * Proporciona una instancia singleton de [ValidateFieldsRegisterPartialUseCase].
     * Caso de uso para validar los campos del formulario de registro de parcial.
     */
    singleOf(::ValidateFieldsRegisterPartialUseCaseImp) {
        bind<ValidateFieldsRegisterPartialUseCase>()
    }
}