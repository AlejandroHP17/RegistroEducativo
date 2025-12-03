package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con escuelas.
 * @author Pelkidev
 * @version 1.0.0
 */


import com.mx.liftechnology.core.network.api.SchoolApi
import com.mx.liftechnology.data.repositoryImpl.school.GetCctRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.schoolCycle.RegisterCycleSchoolRepositoryImpl
import com.mx.liftechnology.domain.repository.school.GetCctRepository
import com.mx.liftechnology.domain.repository.school.RegisterCycleSchoolRepository
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
    factory { get<Retrofit>().create(SchoolApi::class.java) }

    /**
     * Proporciona una instancia singleton de [GetCctRepository].
     */
    singleOf(::GetCctRepositoryImpl){
        bind<GetCctRepository>()
    }

    /**
     * Proporciona una instancia singleton de [RegisterCycleSchoolRepository].
     */
    singleOf(::RegisterCycleSchoolRepositoryImpl){
        bind<RegisterCycleSchoolRepository>()
    }
}