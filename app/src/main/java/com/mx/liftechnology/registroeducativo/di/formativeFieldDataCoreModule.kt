package com.mx.liftechnology.registroeducativo.di

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

val formativeFieldDataCoreModule = module {
    /**
     * Provides an instance of [FormativeFieldApi].
     */
    factory { get<Retrofit>().create(FormativeFieldApi::class.java) }

    /**
     * Provides a singleton instance of [GetWorkTypeRepository].
     */
    singleOf(::DeleteFormativeFieldRepositoryImpl) {
        bind<DeleteFormativeFieldRepository>()
    }

    /**
     * Provides a singleton instance of [GetListFormativeFieldRepository].
     */
    singleOf(::GetListFormativeFieldRepositoryImpl) {
        bind<GetListFormativeFieldRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterUserRepository].
     */
    singleOf(::GetListWotyFofiRepositoryImpl){
        bind<GetListWotyFofiRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterFormativeFieldsBulkRepository].
     */
    singleOf(::RegisterFormativeFieldsBulkRepositoryImpl) {
        bind<RegisterFormativeFieldsBulkRepository>()
    }
}