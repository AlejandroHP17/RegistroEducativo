package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowMain.GetCctApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterSchoolApiCall
import com.mx.liftechnology.data.repository.flowMain.school.GetCctRepository
import com.mx.liftechnology.data.repository.flowMain.school.GetCctRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.school.RegisterSchoolRepository
import com.mx.liftechnology.data.repository.flowMain.school.RegisterSchoolRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.GetCctUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.RegisterOneSchoolUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.ValidateFieldsRegisterSchoolUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.ValidateFieldsRegisterSchoolUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.school.RegisterSchoolViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val registerSchoolModule = module {

    factory { get<Retrofit>().create(GetCctApiCall::class.java) }
    factory { get<Retrofit>().create(RegisterSchoolApiCall::class.java) }

    single<GetCctRepository> {
        GetCctRepositoryImp(get())
    }
    factory{GetCctUseCase(get()) }

    single<RegisterSchoolRepository> {
        RegisterSchoolRepositoryImp(get())
    }
    factory{RegisterOneSchoolUseCase(get(), get())}

    single<ValidateFieldsRegisterSchoolUseCase> {
        ValidateFieldsRegisterSchoolUseCaseImp()
    }

    viewModel {
        RegisterSchoolViewModel(get(), get(), get(), get(), get())
    }
}
