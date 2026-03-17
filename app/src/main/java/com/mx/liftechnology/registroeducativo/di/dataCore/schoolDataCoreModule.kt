package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con escuelas.
 * @author Pelkidev
 * @version 1.0.0
 */

import com.mx.liftechnology.core.network.api.SchoolApi
import com.mx.liftechnology.data.repositoryImpl.school.SchoolRepositoryImpl
import com.mx.liftechnology.domain.repository.school.SchoolRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Módulo de Koin para dependencias de datos relacionadas con escuelas.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val schoolDataCoreModule = module {
    /**
     * Proporciona una instancia factory de [SchoolApi].
     * API de Retrofit para realizar llamadas al servicio de escuelas.
     */
    factory { get<Retrofit>().create(SchoolApi::class.java) }

    /**
     * Proporciona una instancia singleton de [SchoolRepository].
     * Repositorio que agrupa todas las operaciones relacionadas con escuelas:
     * - Obtener CCT (Clave de Centro de Trabajo)
     */
    singleOf(::SchoolRepositoryImpl) {
        bind<SchoolRepository>()
    }
}
