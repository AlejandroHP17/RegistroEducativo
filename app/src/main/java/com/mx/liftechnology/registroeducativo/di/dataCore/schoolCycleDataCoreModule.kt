package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con ciclos escolares.
 * @author Pelkidev
 * @version 1.0.0
 */


import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.data.repositoryImpl.schoolCycle.MenuRepositoryImpl
import com.mx.liftechnology.domain.repository.schoolCycle.menu.MenuRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit


/**
 * Módulo de Koin para dependencias de datos relacionadas con ciclos escolares.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val schoolCycleDataCoreModule = module {

    /**
     * Proporciona una instancia de [SchoolCycleApi].
     */
    factory { get<Retrofit>().create(SchoolCycleApi::class.java) }

    /**
     * Proporciona una instancia singleton de [MenuRepository].
     */
    singleOf(::MenuRepositoryImpl) {
        bind<MenuRepository>()
    }
}