package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.network.callapi.ResponseGetListAssessmentType
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.RegisterOneSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.ValidateFieldsSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assessment.GetListAssessmentTypeUseCase
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterSubjectUIState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import com.mx.liftechnology.registroeducativo.main.viewextensions.stringToModelStateOutFieldText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterSubjectViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsSubjectUseCase: ValidateFieldsSubjectUseCase,
    private val registerOneSubjectUseCase: RegisterOneSubjectUseCase,
    private val getListAssessmentTypeUseCase: GetListAssessmentTypeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterSubjectUIState())
    val uiState: StateFlow<ModelRegisterSubjectUIState> = _uiState.asStateFlow()

    fun onSubjectChanged(subject: String) {
        _uiState.update {
            it.copy(
                subject = subject.stringToModelStateOutFieldText()
            )
        }
    }

    fun onNameChange(value: Pair<ResponseGetListAssessmentType?, Int>) {
        _uiState.update {
            it.copy(
                listAdapter = it.listAdapter?.map { subject ->
                    if (subject.position == value.second) {
                        subject.copy(
                            name = value.first?.description.stringToModelStateOutFieldText(),
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
                        subject.copy(percent = value.first.stringToModelStateOutFieldText())
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
                    name = "".stringToModelStateOutFieldText(),
                    percent = "".stringToModelStateOutFieldText(),
                    assessmentTypeId = -1,
                    teacherSchoolCycleGroupId = 1
                )
            }

            _uiState.update {
                it.copy(
                    options = options.stringToModelStateOutFieldText(),
                    listAdapter = list,
                    subject = it.subject.valueText.stringToModelStateOutFieldText()
                )
            }
        }
    }

    fun validateFieldsCompose() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(dispatcherProvider.io) {
            val nameState = validateFieldsSubjectUseCase.validateNameCompose(_uiState.value.subject.valueText)
            val optionState = validateFieldsSubjectUseCase.validateOptionCompose(_uiState.value.options.valueText)
            val updatedListState = validateFieldsSubjectUseCase.validateListJobsCompose(_uiState.value.listAdapter?.toMutableList())

            _uiState.update {
                it.copy(
                    subject = nameState,
                    options = optionState,
                    listAdapter =  updatedListState
                )
            }

            if (!(nameState.isError || optionState.isError)) {
                val optionState = validateFieldsSubjectUseCase.validPercentCompose(_uiState.value.listAdapter?.toMutableList())

                if(!optionState.isError) registerSubjectCompose()
                _uiState.update {
                    it.copy(
                        options = optionState,
                        isLoading = false)
                }
            }else{
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun registerSubjectCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                registerOneSubjectUseCase.registerOneSubjectCompose(_uiState.value.listAdapter?.toMutableList(), _uiState.value.subject.valueText)
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
}