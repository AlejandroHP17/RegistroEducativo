package com.mx.liftechnology.registroeducativo.di


import com.mx.liftechnology.core.network.apiCall.flowMain.GetCctApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterCycleSchoolApiCall
import com.mx.liftechnology.data.repository.flowMain.school.GetCctRepository
import com.mx.liftechnology.data.repository.flowMain.school.GetCctRepositoryImpl
import com.mx.liftechnology.data.repository.flowMain.school.RegisterCycleSchoolRepository
import com.mx.liftechnology.data.repository.flowMain.school.RegisterCycleSchoolRepositoryImpl
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.GetCctUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.RegisterCycleSchoolUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.ValidateFieldsRegisterSchoolUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.ValidateFieldsRegisterSchoolUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.school.RegisterSchoolViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
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
     * Provides an instance of [RegisterCycleSchoolApiCall].
     */
    factory { get<Retrofit>().create(RegisterCycleSchoolApiCall::class.java) }

    /**
     * Provides an instance of [GetCctUseCase].
     */
    factory{GetCctUseCase(get()) }

    /**
     * Provides a singleton instance of [GetCctRepository].
     */
    singleOf(::GetCctRepositoryImpl){
        bind<GetCctRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterCycleSchoolRepository].
     */
    singleOf(::RegisterCycleSchoolRepositoryImpl){
        bind<RegisterCycleSchoolRepository>()
    }

    /**
     * Provides an instance of [RegisterCycleSchoolUseCase].
     */
    singleOf(::RegisterCycleSchoolUseCase)

    /**
     * Provides a singleton instance of [ValidateFieldsRegisterSchoolUseCase].
     */
    singleOf(::ValidateFieldsRegisterSchoolUseCaseImp){
        bind<ValidateFieldsRegisterSchoolUseCase>()
    }

    /**
     * Provides an instance of [RegisterSchoolViewModel].
     */
    viewModelOf(::RegisterSchoolViewModel)
}
