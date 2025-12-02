package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.data.repositoryImpl.evaluation.RegisterWorkTypeEvaluationsRepositoryImpl
import com.mx.liftechnology.domain.repository.evaluation.RegisterWorkTypeEvaluationsRepository
import com.mx.liftechnology.domain.usecase.evaluation.GetDatesActivePartialUseCase
import com.mx.liftechnology.domain.usecase.evaluation.RegisterWorkTypeEvaluationsUseCase
import com.mx.liftechnology.domain.usecase.evaluation.ValidateFieldsEvaluationUseCase
import com.mx.liftechnology.domain.usecase.evaluation.ValidateFieldsEvaluationUseCaseImp
import com.mx.liftechnology.domain.usecase.formativeField.SaveFormativeFieldIdSelectedUseCase
import com.mx.liftechnology.registroeducativo.main.ui.evaluation.RegisterEvaluationViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Koin module for assignment registration-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val registerEvaluationModule = module {

    /**
     * Provides an instance of [EvaluationApi].
     */
    factory { get<Retrofit>().create(EvaluationApi::class.java) }

    /**
     * Provides a singleton instance of [ValidateFieldsEvaluationUseCase].
     */
    singleOf(::ValidateFieldsEvaluationUseCaseImp) {
        bind<ValidateFieldsEvaluationUseCase>()
    }

    singleOf(::RegisterWorkTypeEvaluationsRepositoryImpl) {
        bind<RegisterWorkTypeEvaluationsRepository>()
    }

    /**
     * Provides a singleton instance of [SaveFormativeFieldIdSelectedUseCase].
     */
    singleOf(::SaveFormativeFieldIdSelectedUseCase)

    /**
     * Provides a singleton instance of [GetDatesActivePartialUseCase].
     */
    singleOf(::GetDatesActivePartialUseCase)

    singleOf(::RegisterWorkTypeEvaluationsUseCase)

    /**
     * Provides an instance of [RegisterEvaluationViewModel].
     */
    viewModelOf(::RegisterEvaluationViewModel)
}
