package com.mx.liftechnology.registroeducativo.di

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

val authDataCoreModule = module {

    /**
     * Provides an instance of [AuthApi].
     */
    factory { get<Retrofit>().create(AuthApi::class.java) }

    /**
     * Provides a singleton instance of [LoginRepository].
     */
    singleOf(::GetDataUserRepositoryImpl) {
        bind<GetDataUserRepository>()
    }

    /**
     * Provides a singleton instance of [LoginRepository].
     */
    singleOf(::LoginRepositoryImpl) {
        bind<LoginRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterUserRepository].
     */
    singleOf(::RegisterUserRepositoryImpl){
        bind<RegisterUserRepository>()
    }
}