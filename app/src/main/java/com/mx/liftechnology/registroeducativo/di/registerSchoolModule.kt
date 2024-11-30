package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.CCTApiCall
import com.mx.liftechnology.data.repository.mainFlow.CCTRepository
import com.mx.liftechnology.data.repository.mainFlow.CCTRepositoryImp
import com.mx.liftechnology.domain.usecase.flowregisterdata.CCTUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.CCTUseCaseImp
import com.mx.liftechnology.domain.usecase.flowregisterdata.RegisterSchoolUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.ValidateFieldsRegisterUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.ValidateFieldsRegisterUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.school.RegisterSchoolViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI to register user and get CCT
 * @author pelkidev
 * @since 1.0.0
 */
val registerSchoolModule = module {

    factory { get<Retrofit>().create(CCTApiCall::class.java) }

    single <CCTRepository>{
        CCTRepositoryImp(get())
    }

    single<CCTUseCase>{
        CCTUseCaseImp(get())
    }
    single <ValidateFieldsRegisterUseCase>{
        ValidateFieldsRegisterUseCaseImp()
    }
    single {
        RegisterSchoolUseCase()
    }

    viewModel {
        RegisterSchoolViewModel(get(), get(), get())
    }
}
