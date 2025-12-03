package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.data.repositoryImpl.schoolCycle.MenuRepositoryImpl
import com.mx.liftechnology.domain.repository.schoolCycle.menu.MenuRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val schoolCycleDataCoreModule = module {

    /**
     * Provides an instance of [SchoolCycleApi].
     */
    factory { get<Retrofit>().create(SchoolCycleApi::class.java) }

    /**
     * Provides a singleton instance of [MenuRepository].
     */
    singleOf(::MenuRepositoryImpl) {
        bind<MenuRepository>()
    }
}