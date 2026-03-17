package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con evaluaciones.
 * @author Pelkidev
 * @version 1.0.0
 */

import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.data.repositoryImpl.evaluation.EvaluationRepositoryImpl
import com.mx.liftechnology.domain.repository.evaluation.EvaluationRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Módulo de Koin para dependencias de datos relacionadas con evaluaciones.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val evaluationDataCoreModule = module {

    /**
     * Proporciona una instancia factory de [EvaluationApi].
     * API de Retrofit para realizar llamadas al servicio de evaluaciones.
     */
    factory { get<Retrofit>().create(EvaluationApi::class.java) }

    /**
     * Proporciona una instancia singleton de [EvaluationRepository].
     * Repositorio que agrupa todas las operaciones relacionadas con evaluaciones:
     * - Registrar evaluaciones
     * - Obtener lista de tipos de trabajo por campo formativo
     * - Obtener evaluaciones de estudiantes
     * - Obtener estudiantes por tipo de campo formativo
     */
    singleOf(::EvaluationRepositoryImpl) {
        bind<EvaluationRepository>()
    }
}
