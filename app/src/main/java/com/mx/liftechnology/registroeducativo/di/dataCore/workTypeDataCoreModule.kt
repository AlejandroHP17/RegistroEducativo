package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con tipos de trabajo.
 * @author Pelkidev
 * @version 2.0.0
 */

import com.mx.liftechnology.core.network.api.WorkTypeApi
import com.mx.liftechnology.data.repositoryImpl.workType.WorkTypeRepositoryImpl
import com.mx.liftechnology.domain.repository.workType.WorkTypeRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Módulo de Koin para dependencias de datos relacionadas con tipos de trabajo.
 *
 * @author Pelkidev
 * @version 2.0.0
 */
val workTypeDataCoreModule = module {

    /**
     * Proporciona una instancia de [WorkTypeApi].
     * Necesaria para operaciones de campos formativos que requieren tipos de trabajo.
     */
    factory { get<Retrofit>().create(WorkTypeApi::class.java) }

    /**
     * Proporciona una instancia singleton de [WorkTypeRepository].
     * Agrupa todas las operaciones relacionadas con tipos de trabajo: obtener tipos de trabajo por campo formativo
     * y obtener lista de tipos de trabajo.
     */
    singleOf(::WorkTypeRepositoryImpl) {
        bind<WorkTypeRepository>()
    }
}