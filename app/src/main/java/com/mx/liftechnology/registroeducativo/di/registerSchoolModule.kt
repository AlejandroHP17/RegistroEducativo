package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.GetCctApiCall
import com.mx.liftechnology.core.network.callapi.RegisterOneSchoolApiCall
import com.mx.liftechnology.data.repository.mainflowdata.CCTRepository
import com.mx.liftechnology.data.repository.mainflowdata.CCTRepositoryImp
import com.mx.liftechnology.data.repository.mainflowdata.school.CrudSchoolRepository
import com.mx.liftechnology.data.repository.mainflowdata.school.CrudSchoolRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.GetCctUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.RegisterOneSchoolUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.ValidateFieldsRegisterSchoolUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.ValidateFieldsRegisterSchoolUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.school.RegisterSchoolViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val registerSchoolModule = module {

    factory { get<Retrofit>().create(GetCctApiCall::class.java) }
    factory { get<Retrofit>().create(RegisterOneSchoolApiCall::class.java) }

    single<CCTRepository> {
        CCTRepositoryImp(get())
    }
    factory{GetCctUseCase(get()) }

    single<CrudSchoolRepository> {
        CrudSchoolRepositoryImp(get())
    }
    factory{RegisterOneSchoolUseCase(get(), get())}

    single<ValidateFieldsRegisterSchoolUseCase> {
        ValidateFieldsRegisterSchoolUseCaseImp()
    }

    viewModel {
        RegisterSchoolViewModel(get(), get(), get(), get(), get())
    }
}
