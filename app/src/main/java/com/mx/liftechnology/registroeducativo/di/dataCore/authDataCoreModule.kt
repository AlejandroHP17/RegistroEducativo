package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con autenticación.
 * @author Pelkidev
 * @version 1.0.0
 */
import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.data.repositoryImpl.auth.GetDataUserRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.auth.LoginRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.auth.RegisterUserRepositoryImpl
import com.mx.liftechnology.domain.repository.auth.GetDataUserRepository
import com.mx.liftechnology.domain.repository.auth.LoginRepository
import com.mx.liftechnology.domain.repository.auth.RegisterUserRepository
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
     * Proporciona una instancia de [AuthApi].
     */
    factory { get<Retrofit>().create(AuthApi::class.java) }

    /**
     * Proporciona una instancia singleton de [GetDataUserRepository].
     */
    singleOf(::GetDataUserRepositoryImpl) {
        bind<GetDataUserRepository>()
    }

    /**
     * Proporciona una instancia singleton de [LoginRepository].
     */
    singleOf(::LoginRepositoryImpl) {
        bind<LoginRepository>()
    }

    /**
     * Proporciona una instancia singleton de [RegisterUserRepository].
     */
    singleOf(::RegisterUserRepositoryImpl){
        bind<RegisterUserRepository>()
    }
}