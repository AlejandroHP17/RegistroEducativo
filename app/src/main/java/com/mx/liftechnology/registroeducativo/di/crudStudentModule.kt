package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.student.DeleteStudentApiCall
import com.mx.liftechnology.core.network.apiCall.student.EditStudentApiCall
import com.mx.liftechnology.core.network.apiCall.student.GetListStudentApiCall
import com.mx.liftechnology.core.network.apiCall.student.RegisterStudentApiCall
import com.mx.liftechnology.data.repository.student.DeleteStudentRepository
import com.mx.liftechnology.data.repository.student.DeleteStudentRepositoryImpl
import com.mx.liftechnology.data.repository.student.EditStudentRepository
import com.mx.liftechnology.data.repository.student.EditStudentRepositoryImpl
import com.mx.liftechnology.data.repository.student.GetStudentRepository
import com.mx.liftechnology.data.repository.student.GetStudentRepositoryImpl
import com.mx.liftechnology.data.repository.student.RegisterStudentRepository
import com.mx.liftechnology.data.repository.student.RegisterStudentRepositoryImpl
import com.mx.liftechnology.domain.usecase.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.ValidateVoiceStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.student.DeleteStudentUseCase
import com.mx.liftechnology.domain.usecase.student.EditStudentUseCase
import com.mx.liftechnology.domain.usecase.student.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.student.ModifyOneStudentUseCase
import com.mx.liftechnology.domain.usecase.student.ModifyOneStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.student.RegisterOneStudentUseCase
import com.mx.liftechnology.domain.usecase.student.ValidateFieldsStudentUseCase
import com.mx.liftechnology.domain.usecase.student.ValidateFieldsStudentUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.list.ListStudentViewModel
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.register.RegisterStudentViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Koin module for student-related CRUD dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val crudStudentModule = module {

    /**
     * Provides an instance of [RegisterStudentApiCall].
     */
    factory { get<Retrofit>().create(RegisterStudentApiCall::class.java) }

    /**
     * Provides an instance of [GetListStudentApiCall].
     */
    factory { get<Retrofit>().create(GetListStudentApiCall::class.java) }

    /**
     * Provides an instance of [DeleteStudentApiCall].
     */
    factory { get<Retrofit>().create(DeleteStudentApiCall::class.java) }

    /**
     * Provides an instance of [EditStudentApiCall].
     */
    factory { get<Retrofit>().create(EditStudentApiCall::class.java) }

    /**
     * Provides a singleton instance of [RegisterStudentRepository].
     */
    singleOf(::RegisterStudentRepositoryImpl) {
        bind<RegisterStudentRepository>()
    }

    /**
     * Provides a singleton instance of [GetStudentRepository].
     */
    singleOf(::GetStudentRepositoryImpl) {
        bind<GetStudentRepository>()
    }
    /**
     * Provides a singleton instance of [GetStudentRepository].
     */
    singleOf(::DeleteStudentRepositoryImpl) {
        bind<DeleteStudentRepository>()
    }
    /**
     * Provides a singleton instance of [GetStudentRepository].
     */
    singleOf(::EditStudentRepositoryImpl) {
        bind<EditStudentRepository>()
    }

    /**
     * Provides a singleton instance of [RegisterOneStudentUseCase].
     */
    singleOf(::RegisterOneStudentUseCase)

    /**
     * Provides a singleton instance of [GetListStudentUseCase].
     */
    singleOf(::GetListStudentUseCase)

    /**
     * Provides a singleton instance of [ModifyOneStudentUseCase].
     */
    singleOf(::ModifyOneStudentUseCaseImp) {
        bind<ModifyOneStudentUseCase>()
    }

    /**
     * Provides a singleton instance of [ValidateVoiceStudentUseCase].
     */
    singleOf(::ValidateVoiceStudentUseCaseImp) {
        bind<ValidateVoiceStudentUseCase>()
    }

    /**
     * Provides a singleton instance of [ValidateFieldsStudentUseCase].
     */
    singleOf(::ValidateFieldsStudentUseCaseImp) {
        bind<ValidateFieldsStudentUseCase>()
    }
    /**
     * Provides a singleton instance of [ValidateFieldsStudentUseCase].
     */
    singleOf(::DeleteStudentUseCase)
    /**
     * Provides a singleton instance of [ValidateFieldsStudentUseCase].
     */
    singleOf(::EditStudentUseCase)

    /**
     * Provides an instance of [RegisterStudentViewModel].
     */
    viewModelOf(::RegisterStudentViewModel)

    /**
     * Provides an instance of [ListStudentViewModel].
     */
    viewModelOf(::ListStudentViewModel)
}
