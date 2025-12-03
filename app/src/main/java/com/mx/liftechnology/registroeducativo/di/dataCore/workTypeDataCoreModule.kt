package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con tipos de trabajo.
 * @author Pelkidev
 * @version 1.0.0
 */

import com.mx.liftechnology.core.network.api.WorkTypeApi
import com.mx.liftechnology.data.repositoryImpl.workType.GetWorkTypeByFormativeFieldsRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.workType.GetWorkTypeRepositoryImpl
import com.mx.liftechnology.domain.repository.evaluation.GetWorkTypeByFormativeFieldsRepository
import com.mx.liftechnology.domain.repository.formativeFields.GetWorkTypeRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Módulo de Koin para dependencias de datos relacionadas con tipos de trabajo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val workTypeDataCoreModule = module {
    /**
     * Proporciona una instancia de [WorkTypeApi].
     */
    factory { get<Retrofit>().create(WorkTypeApi::class.java) }

    singleOf(::GetWorkTypeByFormativeFieldsRepositoryImpl){
        bind<GetWorkTypeByFormativeFieldsRepository>()
    }

    /**
     * Proporciona una instancia singleton de [GetWorkTypeRepository].
     */
    singleOf(::GetWorkTypeRepositoryImpl) {
        bind<GetWorkTypeRepository>()
    }
}