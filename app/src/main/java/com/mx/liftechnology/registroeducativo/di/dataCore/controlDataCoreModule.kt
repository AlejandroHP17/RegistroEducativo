package com.mx.liftechnology.registroeducativo.di.dataCore

import com.mx.liftechnology.core.network.api.ControlApi
import com.mx.liftechnology.data.repositoryImpl.control.ControlRepositoryImpl
import com.mx.liftechnology.domain.repository.control.ControlRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val controlDataCoreModule = module {
    factory { get<Retrofit>().create(ControlApi::class.java) }

    singleOf(::ControlRepositoryImpl){
        bind<ControlRepository>()
    }
}