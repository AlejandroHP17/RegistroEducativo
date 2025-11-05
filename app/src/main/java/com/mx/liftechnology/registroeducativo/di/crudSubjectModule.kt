package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListAssessmentTypeApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.GetListEvaluationTypeApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.GetListSubjectApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterSubjectApiCall
import com.mx.liftechnology.data.repository.flowMain.subject.GetListSubjectRepository
import com.mx.liftechnology.data.repository.flowMain.subject.GetListSubjectRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.subject.RegisterSubjectRepository
import com.mx.liftechnology.data.repository.flowMain.subject.RegisterSubjectRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.subject.assessment.GetAssessmentTypeRepository
import com.mx.liftechnology.data.repository.flowMain.subject.assessment.GetAssessmentTypeRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.subject.evaluationtype.GetListEvaluationTypeRepository
import com.mx.liftechnology.data.repository.flowMain.subject.evaluationtype.GetListEvaluationTypeRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.GetListSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.RegisterOneSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.ValidateFieldsSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.ValidateFieldsSubjectUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assessment.GetListAssessmentTypeUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.evaluationType.GetListEvaluationTypeUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.evaluationType.GetListEvaluationTypeUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.list.ListSubjectViewModel
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.register.RegisterSubjectViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Koin module for subject-related CRUD dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val crudSubjectModule = module {

    /**
     * Provides an instance of [RegisterSubjectApiCall].
     */
    factory { get<Retrofit>().create(RegisterSubjectApiCall::class.java) }

    /**
     * Provides an instance of [GetListSubjectApiCall].
     */
    factory { get<Retrofit>().create(GetListSubjectApiCall::class.java) }

    /**
     * Provides an instance of [GetListEvaluationTypeApiCall].
     */
    factory { get<Retrofit>().create(GetListEvaluationTypeApiCall::class.java) }

    /**
     * Provides an instance of [GetListAssessmentTypeApiCall].
     */
    factory { get<Retrofit>().create(GetListAssessmentTypeApiCall::class.java) }

    /**
     * Provides a singleton instance of [GetListEvaluationTypeRepository].
     */
    singleOf(::GetListEvaluationTypeRepositoryImp) {
        bind<GetListEvaluationTypeRepository>()
    }

    /**
     * Provides a singleton instance of [GetListSubjectRepository].
     */
    singleOf(::GetListSubjectRepositoryImp) {
        bind<GetListSubjectRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterSubjectRepository].
     */
    singleOf(::RegisterSubjectRepositoryImp) {
        bind<RegisterSubjectRepository>()
    }

    /**
     * Provides a singleton instance of [GetAssessmentTypeRepository].
     */
    singleOf(::GetAssessmentTypeRepositoryImp) {
        bind<GetAssessmentTypeRepository>()
    }

    /**
     * Provides a singleton instance of [GetListEvaluationTypeUseCase].
     */
    singleOf(::GetListEvaluationTypeUseCaseImp) {
        bind<GetListEvaluationTypeUseCase>()
    }

    /**
     * Provides a singleton instance of [RegisterOneSubjectUseCase].
     */
    singleOf(::RegisterOneSubjectUseCase)

    /**
     * Provides a singleton instance of [GetListSubjectUseCase].
     */
    singleOf(::GetListSubjectUseCase)

    /**
     * Provides a singleton instance of [GetListAssessmentTypeUseCase].
     */
    singleOf(::GetListAssessmentTypeUseCase)

    /**
     * Provides a singleton instance of [ValidateFieldsSubjectUseCase].
     */
    singleOf(::ValidateFieldsSubjectUseCaseImp) {
        bind<ValidateFieldsSubjectUseCase>()
    }

    /**
     * Provides an instance of [RegisterSubjectViewModel].
     */
    viewModelOf(::RegisterSubjectViewModel)

    /**
     * Provides an instance of [ListSubjectViewModel].
     */
    viewModelOf(::ListSubjectViewModel)
}
