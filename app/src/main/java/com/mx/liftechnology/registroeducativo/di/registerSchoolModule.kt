package com.mx.liftechnology.registroeducativo.di


import com.mx.liftechnology.core.network.apiCall.schoolCycle.GetCctApiCall
import com.mx.liftechnology.core.network.apiCall.schoolCycle.RegisterSchoolCycleApiCall
import com.mx.liftechnology.data.repository.schoolCycle.school.GetCctRepository
import com.mx.liftechnology.data.repository.schoolCycle.school.GetCctRepositoryImpl
import com.mx.liftechnology.data.repository.schoolCycle.school.RegisterCycleSchoolRepository
import com.mx.liftechnology.data.repository.schoolCycle.school.RegisterCycleSchoolRepositoryImpl
import com.mx.liftechnology.domain.usecase.schoolCycle.school.GetCctUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.RegisterCycleSchoolUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.ValidateFieldsRegisterSchoolUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.ValidateFieldsRegisterSchoolUseCaseImp
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
     * Provides an instance of [RegisterSchoolCycleApiCall].
     */
    factory { get<Retrofit>().create(RegisterSchoolCycleApiCall::class.java) }

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
