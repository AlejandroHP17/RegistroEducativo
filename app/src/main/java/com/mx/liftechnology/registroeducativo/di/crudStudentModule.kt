package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.GetListStudentApiCall
import com.mx.liftechnology.core.network.callapi.RegisterStudentApiCall
import com.mx.liftechnology.data.repository.registerFlow.CrudStudentRepository
import com.mx.liftechnology.data.repository.registerFlow.CrudStudentRepositoryImp
import com.mx.liftechnology.domain.usecase.flowdata.student.ValidateFieldsStudentUseCase
import com.mx.liftechnology.domain.usecase.flowdata.student.ValidateFieldsStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.flowdata.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.flowdata.ValidateVoiceStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.flowdata.student.CreateStudentUseCase
import com.mx.liftechnology.domain.usecase.flowdata.student.CreateStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.flowdata.student.ReadStudentUseCase
import com.mx.liftechnology.domain.usecase.flowdata.student.ReadStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.flowdata.student.UpdateStudentUseCase
import com.mx.liftechnology.domain.usecase.flowdata.student.UpdateStudentUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.edit.EditStudentViewModel
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.list.ListStudentViewModel
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.register.RegisterStudentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val crudStudentModule = module {

    factory { get<Retrofit>().create(RegisterStudentApiCall::class.java) }
    factory { get<Retrofit>().create(GetListStudentApiCall::class.java) }

    single<CrudStudentRepository> {
        CrudStudentRepositoryImp(get(), get())
    }

    single<CreateStudentUseCase> {
        CreateStudentUseCaseImp(get(), get())
    }
    single<ReadStudentUseCase> {
        ReadStudentUseCaseImp(get(), get())
    }
    single<UpdateStudentUseCase> {
        UpdateStudentUseCaseImp(get(), get())
    }

    single<ValidateVoiceStudentUseCase> {
        ValidateVoiceStudentUseCaseImp()
    }

    single<ValidateFieldsStudentUseCase> {
        ValidateFieldsStudentUseCaseImp()
    }

    viewModel {
        RegisterStudentViewModel(get(), get(), get(), get())
    }
    viewModel {
        EditStudentViewModel(get(), get(), get())
    }
    viewModel {
        ListStudentViewModel(get())
    }
}
