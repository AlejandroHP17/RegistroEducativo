package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.repositoryImpl.evaluation.GetListByFieldTypeStudentRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.formativeField.GetListWotyFofiRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.workType.GetWorkTypeByFormativeFieldsRepositoryImpl
import com.mx.liftechnology.data.repositoryImpl.evaluation.GetListEvaluationsStudentRepositoryImpl
import com.mx.liftechnology.domain.repository.evaluation.GetWorkTypeByFormativeFieldsRepository
import com.mx.liftechnology.domain.repository.formativeFields.GetListByFieldTypeStudentRepository
import com.mx.liftechnology.domain.repository.formativeFields.GetListWotyFofiRepository
import com.mx.liftechnology.domain.repository.student.GetListEvaluationsStudentRepository
import com.mx.liftechnology.domain.usecase.formativeField.GetListByFieldTypeStudentUseCase
import com.mx.liftechnology.domain.usecase.formativeField.GetListWotyFofiUseCase
import com.mx.liftechnology.domain.usecase.evaluation.GetWorkTypeByFormativeFieldUseCase
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
     * Provides an instance of [FormativeFieldApi].
     */
    factory { get<Retrofit>().create(FormativeFieldApi::class.java) }

    /**
     * Provides an instance of [StudentApi].
     */
    factory { get<Retrofit>().create(StudentApi::class.java) }

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
    singleOf(::GetListByFieldTypeStudentRepositoryImpl){
        bind<GetListByFieldTypeStudentRepository>()
    }

    singleOf(::GetListWotyFofiUseCase)
    singleOf(::GetWorkTypeByFormativeFieldUseCase)
    singleOf(::GetListEvaluationsStudentUseCase)
    singleOf(::GetListByFieldTypeStudentUseCase)


    /**
     * Provides an instance of [WotyFofiStudentViewModel].
     */
    viewModelOf(::WotyFofiStudentViewModel)
    /**
     * Provides an instance of [WotyFofiStudentViewModel].
     */
    viewModelOf(::WotyFofiViewModel)
}
