package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.callapi.GetListEvaluationTypeApiCall
import com.mx.liftechnology.core.network.callapi.GetListSubjectApiCall
import com.mx.liftechnology.core.network.callapi.RegisterOneSubjectApiCall
import com.mx.liftechnology.data.repository.mainflowdata.subject.CrudSubjectRepository
import com.mx.liftechnology.data.repository.mainflowdata.subject.CrudSubjectRepositoryImp
import com.mx.liftechnology.data.repository.mainflowdata.subject.evaluationtype.CrudEvaluationTypeRepository
import com.mx.liftechnology.data.repository.mainflowdata.subject.evaluationtype.CrudEvaluationTypeRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.GetListSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.GetListSubjectUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.RegisterOneSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.RegisterOneSubjectUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.ValidateFieldsSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.ValidateFieldsSubjectUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.evaluationType.GetListEvaluationTypeUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.evaluationType.GetListEvaluationTypeUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.list.ListSubjectViewModel
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.register.RegisterSubjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val crudSubjectModule = module {

    factory { get<Retrofit>().create(RegisterOneSubjectApiCall::class.java) }
    factory { get<Retrofit>().create(GetListSubjectApiCall::class.java) }
    factory { get<Retrofit>().create(GetListEvaluationTypeApiCall::class.java) }

    single<CrudEvaluationTypeRepository> {
        CrudEvaluationTypeRepositoryImp(get())
    }
    single<CrudSubjectRepository> {
        CrudSubjectRepositoryImp(get(), get())
    }

    single<GetListEvaluationTypeUseCase> {
        GetListEvaluationTypeUseCaseImp(get(), get())
    }
    single<RegisterOneSubjectUseCase> {
        RegisterOneSubjectUseCaseImp(get(), get())
    }
    single<GetListSubjectUseCase> {
        GetListSubjectUseCaseImp(get(), get())
    }

    single<ValidateFieldsSubjectUseCase> {
        ValidateFieldsSubjectUseCaseImp()
    }

    viewModel {
        RegisterSubjectViewModel(get(), get(), get(), get())
    }
    viewModel {
        ListSubjectViewModel(get())
    }
}
