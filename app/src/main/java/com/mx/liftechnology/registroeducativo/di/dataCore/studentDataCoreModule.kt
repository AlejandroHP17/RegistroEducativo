package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */

import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.repositoryImpl.student.StudentRepositoryImpl
import com.mx.liftechnology.domain.repository.student.StudentRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Módulo de Koin para dependencias de datos relacionadas con estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val studentDataCoreModule = module {
    /**
     * Proporciona una instancia factory de [StudentApi].
     * API de Retrofit para realizar llamadas al servicio de estudiantes.
     */
    factory { get<Retrofit>().create(StudentApi::class.java) }

    /**
     * Proporciona una instancia singleton de [StudentRepository].
     * Repositorio que agrupa todas las operaciones relacionadas con estudiantes:
     * - Obtener lista de estudiantes
     * - Registrar estudiante
     * - Editar estudiante
     * - Eliminar estudiante
     */
    singleOf(::StudentRepositoryImpl) {
        bind<StudentRepository>()
    }
}