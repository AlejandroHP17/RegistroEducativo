package com.mx.liftechnology.registroeducativo.di.dataCore

/**
 * @file Define el módulo de Koin para dependencias de datos relacionadas con campos formativos.
 * @author Pelkidev
 * @version 1.0.0
 */


import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.repositoryImpl.formativeField.DeleteFormativeFieldRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.formativeField.GetListFormativeFieldRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.formativeField.GetListWotyFofiRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.formativeField.RegisterFormativeFieldsBulkRepositoryImpl
import com.mx.liftechnology.domain.repository.formativeFields.DeleteFormativeFieldRepository
import com.mx.liftechnology.domain.repository.formativeFields.GetListFormativeFieldRepository
import com.mx.liftechnology.domain.repository.formativeFields.GetListWotyFofiRepository
import com.mx.liftechnology.domain.repository.formativeFields.GetWorkTypeRepository
import com.mx.liftechnology.domain.repository.formativeFields.RegisterFormativeFieldsBulkRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Módulo de Koin para dependencias de datos relacionadas con campos formativos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val formativeFieldDataCoreModule = module {
    /**
     * Proporciona una instancia de [FormativeFieldApi].
     */
    factory { get<Retrofit>().create(FormativeFieldApi::class.java) }

    /**
     * Proporciona una instancia singleton de [DeleteFormativeFieldRepository].
     */
    singleOf(::DeleteFormativeFieldRepositoryImpl) {
        bind<DeleteFormativeFieldRepository>()
    }

    /**
     * Proporciona una instancia singleton de [GetListFormativeFieldRepository].
     */
    singleOf(::GetListFormativeFieldRepositoryImpl) {
        bind<GetListFormativeFieldRepository>()
    }

    /**
     * Proporciona una instancia singleton de [GetListWotyFofiRepository].
     */
    singleOf(::GetListWotyFofiRepositoryImpl){
        bind<GetListWotyFofiRepository>()
    }

    /**
     * Proporciona una instancia singleton de [RegisterFormativeFieldsBulkRepository].
     */
    singleOf(::RegisterFormativeFieldsBulkRepositoryImpl) {
        bind<RegisterFormativeFieldsBulkRepository>()
    }
}