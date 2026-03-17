package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con autenticación.
 * @author Pelkidev
 * @version 1.0.0
 */
import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.data.repositoryImpl.auth.AuthRepositoryImpl
import com.mx.liftechnology.domain.repository.auth.AuthRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Módulo de Koin para dependencias de datos relacionadas con autenticación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val authDataCoreModule = module {

    /**
     * Proporciona una instancia factory de [AuthApi].
     * API de Retrofit para realizar llamadas al servicio de autenticación.
     */
    factory { get<Retrofit>().create(AuthApi::class.java) }

    /**
     * Proporciona una instancia singleton de [AuthRepository].
     * Repositorio que agrupa todas las operaciones de autenticación:
     * - Login de usuario
     * - Registro de usuario
     * - Obtención de datos de usuario autenticado
     */
    singleOf(::AuthRepositoryImpl) {
        bind<AuthRepository>()
    }
}