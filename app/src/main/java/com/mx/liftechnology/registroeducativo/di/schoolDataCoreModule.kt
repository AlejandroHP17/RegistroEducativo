package com.mx.liftechnology.registroeducativo.di


import com.mx.liftechnology.core.network.api.SchoolApi
import com.mx.liftechnology.data.repositoryImpl.school.GetCctRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.schoolCycle.RegisterCycleSchoolRepositoryImpl
import com.mx.liftechnology.domain.repository.schoolCycle.school.GetCctRepository
import com.mx.liftechnology.domain.repository.schoolCycle.school.RegisterCycleSchoolRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val schoolDataCoreModule = module {
    factory { get<Retrofit>().create(SchoolApi::class.java) }

    /**
     * Provides a singleton instance of [GetCctRepository].
     */
    singleOf(::GetCctRepositoryImpl){
        bind<GetCctRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterCycleSchoolRepository].
     */
    singleOf(::RegisterCycleSchoolRepositoryImpl){
        bind<RegisterCycleSchoolRepository>()
    }
}