package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.api.WorkTypeApi
import com.mx.liftechnology.data.repositoryImpl.workType.GetWorkTypeByFormativeFieldsRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.workType.GetWorkTypeRepositoryImpl
import com.mx.liftechnology.domain.repository.evaluation.GetWorkTypeByFormativeFieldsRepository
import com.mx.liftechnology.domain.repository.formativeFields.GetWorkTypeRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val workTypeDataCoreModule = module {
    /**
     * Provides an instance of [WorkTypeApi].
     */
    factory { get<Retrofit>().create(WorkTypeApi::class.java) }

    singleOf(::GetWorkTypeByFormativeFieldsRepositoryImpl){
        bind<GetWorkTypeByFormativeFieldsRepository>()
    }

    /**
     * Provides a singleton instance of [GetWorkTypeRepository].
     */
    singleOf(::GetWorkTypeRepositoryImpl) {
        bind<GetWorkTypeRepository>()
    }
}