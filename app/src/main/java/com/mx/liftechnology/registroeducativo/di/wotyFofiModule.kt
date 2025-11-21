package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.auth.RegisterUserApiCall
import com.mx.liftechnology.core.network.apiCall.formativeField.GetListWotyFofiApiCall
import com.mx.liftechnology.core.network.apiCall.formativeField.GetWorkTypeApiCall
import com.mx.liftechnology.core.network.apiCall.student.GetListEvaluationsStudentApiCall
import com.mx.liftechnology.data.repository.auth.RegisterUserRepository
import com.mx.liftechnology.data.repository.formativeField.GetListWotyFofiRepository
import com.mx.liftechnology.data.repository.formativeField.GetListWotyFofiRepositoryImpl
import com.mx.liftechnology.data.repository.formativeField.GetWorkTypeByFormativeFieldsRepository
import com.mx.liftechnology.data.repository.formativeField.GetWorkTypeByFormativeFieldsRepositoryImpl
import com.mx.liftechnology.data.repository.student.GetListEvaluationsStudentRepository
import com.mx.liftechnology.data.repository.student.GetListEvaluationsStudentRepositoryImpl
import com.mx.liftechnology.domain.usecase.formativeField.GetListWotyFofiUseCase
import com.mx.liftechnology.domain.usecase.formativeField.GetWorkTypeByFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.student.GetListEvaluationsStudentUseCase
import com.mx.liftechnology.registroeducativo.main.ui.formativeFields.wotyfofi.WotyFofiViewModel
import com.mx.liftechnology.registroeducativo.main.ui.student.wotyfofi.WotyFofiStudentViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Koin module for assignment-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val wotyFofiModule = module {
    /**
     * Provides an instance of [RegisterUserApiCall].
     */
    factory { get<Retrofit>().create(GetListWotyFofiApiCall::class.java) }
    factory { get<Retrofit>().create(GetWorkTypeApiCall::class.java) }
    factory { get<Retrofit>().create(GetListEvaluationsStudentApiCall::class.java) }

    /**
     * Provides a singleton instance of [RegisterUserRepository].
     */
    singleOf(::GetListWotyFofiRepositoryImpl){
        bind<GetListWotyFofiRepository>()
    }
    singleOf(::GetWorkTypeByFormativeFieldsRepositoryImpl){
        bind<GetWorkTypeByFormativeFieldsRepository>()
    }
    singleOf(::GetListEvaluationsStudentRepositoryImpl){
        bind<GetListEvaluationsStudentRepository>()
    }

    singleOf(::GetListWotyFofiUseCase)
    singleOf(::GetWorkTypeByFormativeFieldUseCase)
    singleOf(::GetListEvaluationsStudentUseCase)


    /**
     * Provides an instance of [WotyFofiStudentViewModel].
     */
    viewModelOf(::WotyFofiStudentViewModel)
    /**
     * Provides an instance of [WotyFofiStudentViewModel].
     */
    viewModelOf(::WotyFofiViewModel)
}
