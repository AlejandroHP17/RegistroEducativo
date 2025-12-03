package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con evaluaciones.
 * @author Pelkidev
 * @version 1.0.0
 */


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

/**
 * Módulo de Koin para dependencias de datos relacionadas con evaluaciones.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val evaluationDataCoreModule = module {

    /**
     * Proporciona una instancia de [EvaluationApi].
     */
    factory { get<Retrofit>().create(EvaluationApi::class.java) }

    singleOf(::GetListByFieldTypeStudentRepositoryImpl){
        bind<GetListByFieldTypeStudentRepository>()
    }

    singleOf(::GetListEvaluationsStudentRepositoryImpl){
        bind<GetListEvaluationsStudentRepository>()
    }

    /**
     * Proporciona una instancia singleton de [GetListWorkTypeFormativeFieldRepository].
     */
    singleOf(::GetListWorkTypeFormativeFieldRepositoryImpl) {
        bind<GetListWorkTypeFormativeFieldRepository>()
    }

    singleOf(::RegisterWorkTypeEvaluationsRepositoryImpl) {
        bind<RegisterWorkTypeEvaluationsRepository>()
    }
}