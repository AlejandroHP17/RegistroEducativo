package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListAssessmentType
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.RegisterOneSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.ValidateFieldsSubjectUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assessment.GetListAssessmentTypeUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterSubjectUIState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Subject Registration screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterSubjectViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsSubjectUseCase: ValidateFieldsSubjectUseCase,
    private val registerOneSubjectUseCase: RegisterOneSubjectUseCase,
    private val getListAssessmentTypeUseCase: GetListAssessmentTypeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterSubjectUIState())
    /** The UI state for the screen. */
    val uiState: StateFlow<ModelRegisterSubjectUIState> = _uiState.asStateFlow()

    /**
     * Called when the subject name changes.
     *
     * @param subject The new subject name.
     */
    fun onSubjectChanged(subject: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    subject = subject.stringToModelStateOutFieldText()
                )
            }
        }
    }

    /**
     * Called when the name of a work method changes.
     *
     * @param value A pair containing the new assessment type and the index of the item that changed.
     */
    fun onNameChange(value: Pair<ResponseGetListAssessmentType?, Int>) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    listAdapter = it.listAdapter?.map { subject ->
                        if (subject.position == value.second) {
                            subject.copy(
                                name = value.first?.description.stringToModelStateOutFieldText(),
                                assessmentTypeId = value.first?.assessmentTypeId,
                                teacherSchoolCycleGroupId = value.first?.teacherSchoolCycleGroupId,
                            )
                        } else {
                            subject
                        }
                    },
                )
            }
        }
    }

    /**
     * Called when the percentage of a work method changes.
     *
     * @param value A pair containing the new percentage and the index of the item that changed.
     */
    fun onPercentChange(value: Pair<String, Int>) {
        viewModelScope.launch(dispatcherProvider.io) {
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
    }

    /**
     * Called when the number of options changes.
     *
     * @param options The new number of options.
     */
    fun onOptionsChanged(options: String) {
        viewModelScope.launch(dispatcherProvider.io) {
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
    }

    /**
     * Validates the input fields and proceeds to register the subject if they are valid.
     */
    fun validateFieldsCompose() {
        viewModelScope.launch (dispatcherProvider.io){
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            val nameState =
                validateFieldsSubjectUseCase.validateNameCompose(_uiState.value.subject.valueText)
            val optionState =
                validateFieldsSubjectUseCase.validateOptionCompose(_uiState.value.options.valueText)
            val updatedListState =
                validateFieldsSubjectUseCase.validateListJobsCompose(_uiState.value.listAdapter?.toMutableList())

            _uiState.update {
                it.copy(
                    subject = nameState,
                    options = optionState,
                    listAdapter = updatedListState
                )
            }

            if (!(nameState.isError || optionState.isError)) {
                val optionState = validateFieldsSubjectUseCase.validPercentCompose(_uiState.value.listAdapter?.toMutableList())

                _uiState.update { it.copy(options = optionState) }

                if (!optionState.isError) {
                    registerSubjectCompose()
                }else{
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                }
            } else {
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    private suspend fun registerSubjectCompose() {
        when (val result =
            registerOneSubjectUseCase.invoke(
                _uiState.value.listAdapter?.toMutableList(),
                _uiState.value.subject.valueText
            )){

            is SuccessState -> {
                _uiState.update { it.copy(
                    uiState = ModelStateUIEnum.SUCCESS,
                    controlToast = ModelStateToastUI(
                        messageToast = R.string.toast_success_register_subject,
                        showToast = true,
                        typeToast = ModelStateTypeToastUI.SUCCESS
                    )
                ) }
            }
            is ErrorUserState -> {
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.ERROR,
                        controlToast = ModelStateToastUI(
                            messageToast = R.string.toast_error_register_subject,
                            showToast = true,
                            typeToast = ModelStateTypeToastUI.ERROR
                        )
                    )
                }
            }
            else -> {
                logs(result.toString())
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.ERROR
                    )
                }
            }
        }
    }

    /**
     * Gets the list of assessment types.
     */
    fun getListAssessmentType() {
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = getListAssessmentTypeUseCase.invoke()
            ) {
                is SuccessState -> {
                    val list = result.result.toMutableList()
                    list.add(
                        ResponseGetListAssessmentType(
                            assessmentTypeId = -1,
                            description = "Nuevo",
                            teacherSchoolCycleGroupId = 1
                        ),
                    )
                    _uiState.update {
                        it.copy(
                            listWorkMethods = list
                        )
                    }
                }

                else -> {
                    logs(result.toString())
                    _uiState.update {
                        it.copy(
                            listWorkMethods = listOf(
                                ResponseGetListAssessmentType(
                                    assessmentTypeId = -1,
                                    description = "Nuevo",
                                    teacherSchoolCycleGroupId = 1
                                ),
                            )
                        )
                    }
                }
            }
        }
    }

    /**
     * Modifies the visibility of the toast message.
     *
     * @param show True to show the toast, false to hide it.
     */
    fun modifyShowToast(show: Boolean) {
        viewModelScope.launch (dispatcherProvider.main){
            _uiState.update {
                it.copy(
                    controlToast = ModelStateToastUI(
                        messageToast = it.controlToast.messageToast,
                        showToast = show,
                        typeToast = it.controlToast.typeToast
                    )
                )
            }
        }
    }
}