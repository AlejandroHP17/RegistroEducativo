package com.mx.liftechnology.registroeducativo.di.dataCore

import com.mx.liftechnology.core.network.api.PartialApi
import com.mx.liftechnology.data.repositoryImpl.partial.PartialRepositoryImpl
import com.mx.liftechnology.domain.repository.partial.PartialRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Módulo de Koin para dependencias de datos relacionadas con parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val partialDataCoreModule = module {
    /**
     * Proporciona una instancia factory de [PartialApi].
     * API de Retrofit para realizar llamadas al servicio de parciales.
     */
    factory { get<Retrofit>().create(PartialApi::class.java) }

    /**
     * Proporciona una instancia singleton de [PartialRepository].
     * Repositorio que agrupa todas las operaciones relacionadas con parciales:
     * - Obtener lista de parciales
     * - Registrar lista de parciales
     */
    singleOf(::PartialRepositoryImpl) {
        bind<PartialRepository>()
    }
}
