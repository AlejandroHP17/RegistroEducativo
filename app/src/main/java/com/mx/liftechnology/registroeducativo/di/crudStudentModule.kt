package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListStudentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterStudentApiCall
import com.mx.liftechnology.data.repository.flowMain.student.GetStudentRepository
import com.mx.liftechnology.data.repository.flowMain.student.GetStudentRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.student.RegisterStudentRepository
import com.mx.liftechnology.data.repository.flowMain.student.RegisterStudentRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.ValidateVoiceStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ModifyOneStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ModifyOneStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.RegisterOneStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ValidateFieldsStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ValidateFieldsStudentUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.list.ListStudentViewModel
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.register.RegisterStudentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
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
     * Provides a singleton instance of [RegisterStudentRepository].
     */
    single<RegisterStudentRepository> {
        RegisterStudentRepositoryImp(get())
    }

    /**
     * Provides a singleton instance of [GetStudentRepository].
     */
    single<GetStudentRepository> {
        GetStudentRepositoryImp(get())
    }

    /**
     * Provides a singleton instance of [RegisterOneStudentUseCase].
     */
    single {RegisterOneStudentUseCase(get(), get())}

    /**
     * Provides a singleton instance of [GetListStudentUseCase].
     */
    single{GetListStudentUseCase(get(), get())}

    /**
     * Provides a singleton instance of [ModifyOneStudentUseCase].
     */
    single<ModifyOneStudentUseCase> {
        ModifyOneStudentUseCaseImp(get(), get())
    }

    /**
     * Provides a singleton instance of [ValidateVoiceStudentUseCase].
     */
    single<ValidateVoiceStudentUseCase> {
        ValidateVoiceStudentUseCaseImp()
    }

    /**
     * Provides a singleton instance of [ValidateFieldsStudentUseCase].
     */
    single<ValidateFieldsStudentUseCase> {
        ValidateFieldsStudentUseCaseImp()
    }

    /**
     * Provides an instance of [RegisterStudentViewModel].
     */
    viewModel {
        RegisterStudentViewModel(get(), get(), get(), get(), get())
    }

    /**
     * Provides an instance of [ListStudentViewModel].
     */
    viewModel {
        ListStudentViewModel(get(), get())
    }
}
