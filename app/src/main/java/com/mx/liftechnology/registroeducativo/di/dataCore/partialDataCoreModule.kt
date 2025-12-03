package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con parciales.
 * @author Pelkidev
 * @version 1.0.0
 */


import com.mx.liftechnology.core.network.api.PartialApi
import com.mx.liftechnology.data.repositoryImpl.partial.GetListPartialRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.partial.RegisterListPartialRepositoryImpl
import com.mx.liftechnology.domain.repository.partial.GetListPartialRepository
import com.mx.liftechnology.domain.repository.partial.RegisterListPartialRepository
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

    factory { get<Retrofit>().create(PartialApi::class.java) }

    /**
     * Proporciona una instancia singleton de [GetListPartialRepository].
     */
    singleOf(::GetListPartialRepositoryImpl){
        bind<GetListPartialRepository>()
    }

    /**
     * Proporciona una instancia singleton de [RegisterListPartialRepository].
     */
    singleOf(::RegisterListPartialRepositoryImpl){
        bind<RegisterListPartialRepository>()
    }
}