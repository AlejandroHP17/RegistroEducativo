package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */


import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.repositoryImpl.student.DeleteStudentRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.student.EditStudentRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.student.GetStudentRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.student.RegisterStudentRepositoryImpl
import com.mx.liftechnology.domain.repository.student.DeleteStudentRepository
import com.mx.liftechnology.domain.repository.student.EditStudentRepository
import com.mx.liftechnology.domain.repository.student.GetStudentRepository
import com.mx.liftechnology.domain.repository.student.RegisterStudentRepository
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
     * Proporciona una instancia singleton de [DeleteStudentRepository].
     */
    singleOf(::DeleteStudentRepositoryImpl) {
        bind<DeleteStudentRepository>()
    }

    /**
     * Proporciona una instancia singleton de [EditStudentRepository].
     */
    singleOf(::EditStudentRepositoryImpl) {
        bind<EditStudentRepository>()
    }

    /**
     * Proporciona una instancia singleton de [GetStudentRepository].
     */
    singleOf(::GetStudentRepositoryImpl) {
        bind<GetStudentRepository>()
    }

    /**
     * Proporciona una instancia singleton de [RegisterStudentRepository].
     */
    singleOf(::RegisterStudentRepositoryImpl) {
        bind<RegisterStudentRepository>()
    }
}