package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowMain.GetCctApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterSchoolApiCall
import com.mx.liftechnology.data.repository.flowMain.school.GetCctRepository
import com.mx.liftechnology.data.repository.flowMain.school.GetCctRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.school.RegisterSchoolRepository
import com.mx.liftechnology.data.repository.flowMain.school.RegisterSchoolRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.GetCctUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.RegisterOneSchoolUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.ValidateFieldsRegisterSchoolUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.ValidateFieldsRegisterSchoolUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.school.RegisterSchoolViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Koin module for school registration-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val registerSchoolModule = module {

    /**
     * Provides an instance of [GetCctApiCall].
     */
    factory { get<Retrofit>().create(GetCctApiCall::class.java) }

    /**
     * Provides an instance of [RegisterSchoolApiCall].
     */
    factory { get<Retrofit>().create(RegisterSchoolApiCall::class.java) }

    /**
     * Provides a singleton instance of [GetCctRepository].
     */
    single<GetCctRepository> {
        GetCctRepositoryImp(get())
    }

    /**
     * Provides an instance of [GetCctUseCase].
     */
    factory{GetCctUseCase(get()) }

    /**
     * Provides a singleton instance of [RegisterSchoolRepository].
     */
    single<RegisterSchoolRepository> {
        RegisterSchoolRepositoryImp(get())
    }

    /**
     * Provides an instance of [RegisterOneSchoolUseCase].
     */
    factory{RegisterOneSchoolUseCase(get(), get())}

    /**
     * Provides a singleton instance of [ValidateFieldsRegisterSchoolUseCase].
     */
    single<ValidateFieldsRegisterSchoolUseCase> {
        ValidateFieldsRegisterSchoolUseCaseImp()
    }

    /**
     * Provides an instance of [RegisterSchoolViewModel].
     */
    viewModel {
        RegisterSchoolViewModel(get(), get(), get(), get(), get())
    }
}
