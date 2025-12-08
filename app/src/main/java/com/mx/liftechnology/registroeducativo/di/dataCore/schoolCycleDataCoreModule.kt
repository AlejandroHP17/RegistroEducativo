package com.mx.liftechnology.registroeducativo.di.dataCore

import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.data.repositoryImpl.menu.MenuLocalRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.schoolCycle.SchoolCycleRepositoryImpl
import com.mx.liftechnology.domain.repository.menu.MenuLocalRepository
import com.mx.liftechnology.domain.repository.schoolCycle.SchoolCycleRepository
import org.koin.android.ext.koin.androidContext
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
     * Proporciona una instancia singleton de [MenuLocalRepository].
     * Repositorio local para acceder a los datos del menú desde los recursos de la aplicación.
     */
    single<MenuLocalRepository> { MenuLocalRepositoryImpl(androidContext()) }

    /**
     * Proporciona una instancia de [SchoolCycleApi].
     */
    factory { get<Retrofit>().create(SchoolCycleApi::class.java) }

    /**
     * Proporciona una instancia singleton de [SchoolCycleRepository].
     * Agrupa todas las operaciones relacionadas con ciclos escolares: obtener lista de ciclos escolares
     * y registrar ciclo escolar.
     */
    singleOf(::SchoolCycleRepositoryImpl) {
        bind<SchoolCycleRepository>()
    }
}
