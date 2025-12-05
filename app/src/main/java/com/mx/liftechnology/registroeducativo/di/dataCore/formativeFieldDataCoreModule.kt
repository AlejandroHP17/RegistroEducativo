package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con campos formativos.
 * @author Pelkidev
 * @version 2.0.0
 */

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.core.network.api.WorkTypeApi
import com.mx.liftechnology.data.repositoryImpl.formativeField.FormativeFieldRepositoryImpl
import com.mx.liftechnology.domain.repository.formativeFields.FormativeFieldRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Módulo de Koin para dependencias de datos relacionadas con campos formativos.
 *
 * @author Pelkidev
 * @version 2.0.0
 */
val formativeFieldDataCoreModule = module {
    /**
     * Proporciona una instancia de [FormativeFieldApi].
     */
    factory { get<Retrofit>().create(FormativeFieldApi::class.java) }

    /**
     * Proporciona una instancia singleton de [FormativeFieldRepository].
     * Agrupa todas las operaciones relacionadas con campos formativos: obtener lista, registrar, eliminar
     * y obtener lista WotyFofi.
     */
    singleOf(::FormativeFieldRepositoryImpl) {
        bind<FormativeFieldRepository>()
    }
}
