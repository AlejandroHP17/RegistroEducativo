package com.mx.liftechnology.registroeducativo.di


import com.mx.liftechnology.core.network.api.PartialApi
import com.mx.liftechnology.data.repositoryImpl.partial.GetListPartialRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.partial.RegisterListPartialRepositoryImpl
import com.mx.liftechnology.domain.repository.schoolCycle.partial.GetListPartialRepository
import com.mx.liftechnology.domain.repository.schoolCycle.partial.RegisterListPartialRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val partialDataCoreModule = module {

    factory { get<Retrofit>().create(PartialApi::class.java) }

    /**
     * Provides a singleton instance of [GetListPartialRepository].
     */
    singleOf(::GetListPartialRepositoryImpl){
        bind<GetListPartialRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterListPartialRepository].
     */
    singleOf(::RegisterListPartialRepositoryImpl){
        bind<RegisterListPartialRepository>()
    }
}