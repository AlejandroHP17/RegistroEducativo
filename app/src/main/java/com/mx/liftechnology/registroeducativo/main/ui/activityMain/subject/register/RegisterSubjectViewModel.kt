package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.network.callapi.ResponseGetListAssessmentType
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.RegisterOneSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.ValidateFieldsSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assessment.GetListAssessmentTypeUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.evaluationType.GetListEvaluationTypeUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterSubjectUIState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterSubjectViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsSubjectUseCase: ValidateFieldsSubjectUseCase,
    private val registerOneSubjectUseCase: RegisterOneSubjectUseCase,
    private val getListEvaluationTypeUseCase: GetListEvaluationTypeUseCase,
    private val getListAssessmentTypeUseCase: GetListAssessmentTypeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterSubjectUIState())
    val uiState: StateFlow<ModelRegisterSubjectUIState> = _uiState.asStateFlow()

    fun onSubjectChanged(subject: String) {
        _uiState.update {
            it.copy(
                subject = subject,
                isErrorSubject = ModelStateOutFieldText(false, "")
            )
        }
    }

    fun onNameChange(value: Pair<ResponseGetListAssessmentType?, Int>) {
        _uiState.update {
            it.copy(
                listAdapter = it.listAdapter?.map { subject ->
                    if (subject.position == value.second) {
                        subject.copy(
                            name = value.first?.description,
                            assessmentTypeId =  value.first?.assessmentTypeId,
                            teacherSchoolCycleGroupId =  value.first?.teacherSchoolCycleGroupId,
                        )
                    } else {
                        subject
                    }
                },
            )
        }
    }

    fun onPercentChange(value: Pair<String, Int>) {
        _uiState.update {
            it.copy(
                listAdapter = it.listAdapter?.map { subject ->
                    if (subject.position == value.second) {
                        subject.copy(percent = value.first)
                    } else {
                        subject
                    }
                },
            )
        }
    }

    fun onOptionsChanged(options: String) {
        if (options.toInt() > 0) {
            val list = MutableList(options.toInt()) { index ->
                ModelSpinnersWorkMethods(
                    position = index,
                    name = "",
                    percent = "",
                    assessmentTypeId = -1,
                    teacherSchoolCycleGroupId = 1
                )
            }

            _uiState.update {
                it.copy(
                    options = options,
                    listAdapter = list,
                    isErrorSubject = ModelStateOutFieldText(false, "")
                )
            }
        }
    }

    fun validateFieldsCompose() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(dispatcherProvider.io) {
            val nameState = validateFieldsSubjectUseCase.validateNameCompose(_uiState.value.subject)
            val updatedListState = validateFieldsSubjectUseCase.validateListJobsCompose(_uiState.value.listAdapter?.toMutableList())

            _uiState.update {
                it.copy(
                    isErrorSubject = nameState,
                    isErrorOption = updatedListState
                )
            }

            if (!(nameState.isError && updatedListState.isError)) {
                registerSubjectCompose()
            }else{
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun registerSubjectCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                registerOneSubjectUseCase.registerOneSubjectCompose(_uiState.value.listAdapter?.toMutableList(), _uiState.value.subject)
            }.onSuccess { state ->
                if(state is SuccessState){
                    _uiState.update { it.copy(
                        isLoading = false,
                        isSuccess = true
                    ) }
                }else{
                    _uiState.update { it.copy(
                        isLoading = false,
                        isSuccess = false
                    ) }
                }
            }.onFailure {
                _uiState.update { it.copy(
                    isLoading = false,
                    isSuccess = false
                ) }
            }
        }
    }

    fun getListAssessmentType() {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                getListAssessmentTypeUseCase.getListAssessmentType()
            }.onSuccess { state ->
                if(state is SuccessState){
                    val list = state.result.toMutableList()
                    list.add(ResponseGetListAssessmentType(
                        assessmentTypeId = -1,
                        description = "Nuevo",
                        teacherSchoolCycleGroupId = 1),
                    )
                    _uiState.update { it.copy(
                        listWorkMethods = list
                    ) }
                }else{
                    _uiState.update { it.copy(
                        listWorkMethods = listOf (ResponseGetListAssessmentType(
                            assessmentTypeId = -1,
                            description = "Nuevo",
                            teacherSchoolCycleGroupId = 1),
                        ) )}
                }
            }.onFailure {
                _uiState.update { it.copy(
                    listWorkMethods = listOf (ResponseGetListAssessmentType(
                        assessmentTypeId = -1,
                        description = "Nuevo",
                        teacherSchoolCycleGroupId = 1),
                    ) )}
            }
        }
    }



    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // Observer the period select by user
    private val _subjectNumber = SingleLiveEvent<Int>()
    val subjectNumber: LiveData<Int> get() = _subjectNumber

    // Observer the period select by user
    private val _nameField = SingleLiveEvent<ModelState<String, String>>()
    val nameField: LiveData<ModelState<String, String>> get() = _nameField

    // Observer the date selected by user
    private val _adapterField = SingleLiveEvent<ModelState<String, String>>()
    val adapterField: LiveData<ModelState<String, String>> get() = _adapterField

    // Observer the response
    private val _responseSubjectRegister = SingleLiveEvent<ModelState<List<String?>?, String>?>()
    val responseSubjectRegister: LiveData<ModelState<List<String?>?, String>?> get() = _responseSubjectRegister

    private val _responseEvaluationType = SingleLiveEvent<ModelState<List<String>?, String>?>()
    val responseEvaluationType: LiveData<ModelState<List<String>?, String>?> get() = _responseEvaluationType

    private val _responseAssessmentType =
        SingleLiveEvent<ModelState<List<ResponseGetListAssessmentType?>, String?>>()
    val responseAssessmentType: LiveData<ModelState<List<ResponseGetListAssessmentType?>, String?>> get() = _responseAssessmentType


    fun saveSubject(data: String?) {
        val subjectNumber = data?.toIntOrNull() ?: 0
        _subjectNumber.postValue(subjectNumber)
    }

    fun validateFields(updatedList: MutableList<ModelFormatSubjectDomain>?, name: String?) {
        viewModelScope.launch(dispatcherProvider.io) {
            _animateLoader.postValue(LoaderState(true))
            val nameState = validateFieldsSubjectUseCase.validateName(name)
            val updatedListState = validateFieldsSubjectUseCase.validateListJobs(updatedList)

            _nameField.postValue(nameState)
            _adapterField.postValue(updatedListState)

            if (nameState is SuccessState && updatedListState is SuccessState) {
                registerSubject(updatedList, name)
            }
        }
    }

    private fun registerSubject(
        updatedList: MutableList<ModelFormatSubjectDomain>?,
        name: String?,
    ) {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                registerOneSubjectUseCase.registerOneSubject(updatedList, name)
            }.onSuccess {
                _animateLoader.postValue(LoaderState(false))
                _responseSubjectRegister.postValue(it)
            }.onFailure {
                _animateLoader.postValue(LoaderState(false))
                _responseSubjectRegister.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }

    fun getListEvaluationType() {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                getListEvaluationTypeUseCase.getListEvaluationType()
            }.onSuccess {
                _animateLoader.postValue(LoaderState(false))
                _responseEvaluationType.postValue(it)
            }.onFailure {
                _animateLoader.postValue(LoaderState(false))
                _responseEvaluationType.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }

    fun loaderState(visible: Boolean) {
        _animateLoader.postValue(LoaderState(visible))
    }


}