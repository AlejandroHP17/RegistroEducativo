package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.data.repositoryImpl.evaluation.GetListByFieldTypeStudentRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.evaluation.GetListEvaluationsStudentRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.evaluation.GetListWorkTypeFormativeFieldRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.evaluation.RegisterWorkTypeEvaluationsRepositoryImpl
import com.mx.liftechnology.domain.repository.evaluation.GetListWorkTypeFormativeFieldRepository
import com.mx.liftechnology.domain.repository.evaluation.RegisterWorkTypeEvaluationsRepository
import com.mx.liftechnology.domain.repository.formativeFields.GetListByFieldTypeStudentRepository
import com.mx.liftechnology.domain.repository.student.GetListEvaluationsStudentRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val evaluationDataCoreModule = module {

    /**
     * Provides an instance of [EvaluationApi].
     */
    factory { get<Retrofit>().create(EvaluationApi::class.java) }

    singleOf(::GetListByFieldTypeStudentRepositoryImpl){
        bind<GetListByFieldTypeStudentRepository>()
    }

    singleOf(::GetListEvaluationsStudentRepositoryImpl){
        bind<GetListEvaluationsStudentRepository>()
    }

    /**
     * Provides a singleton instance of [GetListWorkTypeFormativeFieldRepository].
     */
    singleOf(::GetListWorkTypeFormativeFieldRepositoryImpl) {
        bind<GetListWorkTypeFormativeFieldRepository>()
    }

    singleOf(::RegisterWorkTypeEvaluationsRepositoryImpl) {
        bind<RegisterWorkTypeEvaluationsRepository>()
    }
}