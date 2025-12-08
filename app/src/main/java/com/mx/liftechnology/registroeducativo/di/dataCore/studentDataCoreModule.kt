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
     * Proporciona una instancia de [StudentApi].
     */
    factory { get<Retrofit>().create(StudentApi::class.java) }

    /**
     * Proporciona una instancia singleton de [StudentRepository].
     * Agrupa todas las operaciones relacionadas con estudiantes: obtener, registrar, editar y eliminar.
     */
    singleOf(::StudentRepositoryImpl) {
        bind<StudentRepository>()
    }
}