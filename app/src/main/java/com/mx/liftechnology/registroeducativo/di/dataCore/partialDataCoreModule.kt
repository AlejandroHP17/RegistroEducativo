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
     * Proporciona una instancia de [PartialApi].
     */
    factory { get<Retrofit>().create(PartialApi::class.java) }

    /**
     * Proporciona una instancia singleton de [PartialRepository].
     * Agrupa todas las operaciones relacionadas con parciales: obtener lista y registrar lista.
     */
    singleOf(::PartialRepositoryImpl) {
        bind<PartialRepository>()
    }
}
