package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListAssessmentTypeApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.GetListEvaluationTypeApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.GetListSubjectApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterSubjectApiCall
import com.mx.liftechnology.data.repository.flowMain.subject.GetListSubjectRepository
import com.mx.liftechnology.data.repository.flowMain.subject.GetListSubjectRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.subject.RegisterSubjectRepository
import com.mx.liftechnology.data.repository.flowMain.subject.RegisterSubjectRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.subject.assessment.GetAssessmentTypeRepository
import com.mx.liftechnology.data.repository.flowMain.subject.assessment.GetAssessmentTypeRepositoryImp
import com.mx.liftechnology.data.repository.flowMain.subject.evaluationtype.GetListEvaluationTypeRepository
import com.mx.liftechnology.data.repository.flowMain.subject.evaluationtype.GetListEvaluationTypeRepositoryImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.GetListSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.RegisterOneSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.ValidateFieldsSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.ValidateFieldsSubjectUseCaseImp
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assessment.GetListAssessmentTypeUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.evaluationType.GetListEvaluationTypeUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.evaluationType.GetListEvaluationTypeUseCaseImp
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.list.ListSubjectViewModel
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.register.RegisterSubjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val crudSubjectModule = module {

    factory { get<Retrofit>().create(RegisterSubjectApiCall::class.java) }
    factory { get<Retrofit>().create(GetListSubjectApiCall::class.java) }
    factory { get<Retrofit>().create(GetListEvaluationTypeApiCall::class.java) }
    factory { get<Retrofit>().create(GetListAssessmentTypeApiCall::class.java) }

    single<GetListEvaluationTypeRepository> {
        GetListEvaluationTypeRepositoryImp(get())
    }
    single<GetListSubjectRepository> {
        GetListSubjectRepositoryImp(get())
    }
    single<RegisterSubjectRepository> {
        RegisterSubjectRepositoryImp(get())
    }
    single<GetAssessmentTypeRepository> {
        GetAssessmentTypeRepositoryImp(get())
    }

    single<GetListEvaluationTypeUseCase> {
        GetListEvaluationTypeUseCaseImp(get(), get())
    }
    single {RegisterOneSubjectUseCase(get(), get())}

    single {GetListSubjectUseCase(get(), get())}

    single {GetListAssessmentTypeUseCase(get(), get())}

    single<ValidateFieldsSubjectUseCase> {
        ValidateFieldsSubjectUseCaseImp()
    }

    viewModel {
        RegisterSubjectViewModel(get(), get(), get(), get())
    }
    viewModel {
        ListSubjectViewModel(get(), get())
    }
}
