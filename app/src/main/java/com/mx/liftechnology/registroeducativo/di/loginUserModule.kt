package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.data.repository.auth.GetDataUserRepository
import com.mx.liftechnology.data.repository.auth.GetDataUserRepositoryImpl
import com.mx.liftechnology.data.repository.auth.LoginRepository
import com.mx.liftechnology.data.repository.auth.LoginRepositoryImpl
import com.mx.liftechnology.domain.usecase.auth.ValidateLoginFieldsUseCase
import com.mx.liftechnology.domain.usecase.auth.ValidateLoginFieldsUseCaseImp
import com.mx.liftechnology.domain.usecase.auth.GetDataUserUseCase
import com.mx.liftechnology.domain.usecase.auth.LoginUseCase
import com.mx.liftechnology.registroeducativo.main.ui.auth.login.LoginViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Koin module for login-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val loginUserModule = module {

    /**
     * Provides an instance of [AuthApi].
     */
    factory { get<Retrofit>().create(AuthApi::class.java) }

    /**
     * Provides a singleton instance of [LoginRepository].
     */
    singleOf(::LoginRepositoryImpl) {
        bind<LoginRepository>()
    }

    /**
     * Provides a singleton instance of [LoginRepository].
     */
    singleOf(::GetDataUserRepositoryImpl) {
        bind<GetDataUserRepository>()
    }

    /**
     * Provides a singleton instance of [LoginUseCase].
     */
    singleOf(::LoginUseCase)

    /**
     * Provides a singleton instance of [GetDataUserUseCase].
     */
    singleOf(::GetDataUserUseCase)

    /**
     * Provides a singleton instance of [ValidateLoginFieldsUseCase].
     */
    singleOf(::ValidateLoginFieldsUseCaseImp) {
        bind<ValidateLoginFieldsUseCase>()
    }

    /**
     * Provides an instance of [LoginViewModel].
     */
    viewModelOf(::LoginViewModel)
}
