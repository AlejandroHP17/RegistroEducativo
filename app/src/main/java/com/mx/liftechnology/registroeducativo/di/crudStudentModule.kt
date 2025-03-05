package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.GetListStudentApiCall
import com.mx.liftechnology.core.network.callapi.RegisterOneStudentApiCall
import com.mx.liftechnology.data.repository.mainflowdata.student.CrudStudentRepository
import com.mx.liftechnology.data.repository.mainflowdata.student.CrudStudentRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ValidateFieldsStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ValidateFieldsStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.ValidateVoiceStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.RegisterOneStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.RegisterOneStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.GetListStudentUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ModifyOneStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ModifyOneStudentUseCaseImp
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

    factory { get<Retrofit>().create(RegisterOneStudentApiCall::class.java) }
    factory { get<Retrofit>().create(GetListStudentApiCall::class.java) }

    single<CrudStudentRepository> {
        CrudStudentRepositoryImp(get(), get())
    }

    single<RegisterOneStudentUseCase> {
        RegisterOneStudentUseCaseImp(get(), get())
    }
    single<GetListStudentUseCase> {
        GetListStudentUseCaseImp(get(), get())
    }
    single<ModifyOneStudentUseCase> {
        ModifyOneStudentUseCaseImp(get(), get())
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
