package com.mx.liftechnology.registroeducativo.main.ui.activityMain.school

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.VoiceRecognitionManager
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.CCTUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.RegisterOneSchoolUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.ValidateFieldsRegisterSchoolUseCase
import com.mx.liftechnology.registroeducativo.main.funextensions.log
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterSchoolUIState
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_error
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_success
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import com.mx.liftechnology.registroeducativo.main.viewextensions.stringToModelStateOutFieldText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterSchoolViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val cctUseCase: CCTUseCase,
    private val validateFieldsUseCase: ValidateFieldsRegisterSchoolUseCase,
    private val registerOneSchoolUseCase: RegisterOneSchoolUseCase,
    private val voiceRecognitionManager: VoiceRecognitionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterSchoolUIState())
    val uiState: StateFlow<ModelRegisterSchoolUIState> = _uiState.asStateFlow()

    init {
        // Observa cambios del reconocimiento de voz
        voiceRecognitionManager.resultsLiveData.observeForever { results ->
            log(results.toString())
            validateData(results)
        }
    }

    private var isListening = true

    fun onCycleChanged(cycle: String) {
        _uiState.update {
            it.copy(
                cycle = cycle.stringToModelStateOutFieldText()
            )
        }
    }

    fun onGradeChanged(grade: String) {
        _uiState.update {
            it.copy(
                grade = grade.stringToModelStateOutFieldText()
            )
        }
    }

    fun onGroupChanged(group: String) {
        _uiState.update {
            it.copy(
                group = group.stringToModelStateOutFieldText()
            )
        }
    }

    fun onCctChanged(cct: String) {
        log(cct.length.toString())
        _uiState.update {
            if (cct.length == 10) {
                getSchoolCCT(cct)
                it.copy(
                    isLoading = true,
                    cct = ModelStateOutFieldText(
                        valueText = cct.uppercase(),
                        isError = false,
                        errorMessage = "")
                )
            } else {
                it.copy(
                    cct = ModelStateOutFieldText(
                        valueText = cct.uppercase(),
                        isError = true,
                        errorMessage = ModelCodeInputs.ET_NOT_FOUND
                    ),
                    schoolName =  it.schoolName.copy(valueText = ""),
                    shift = it.shift.copy(valueText = ""),
                    type = it.type.copy(valueText = ""),
                    read = true
                )
            }
        }
    }

    /** Go to validate  the cct
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun getSchoolCCT(cct: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                cctUseCase.getSchoolCCTCompose(cct)
            }.onSuccess { state ->
                if (state is SuccessState) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            schoolName = it.schoolName.copy(valueText = state.result?.result?.schoolName ?: ""),
                            schoolCycleTypeId = state.result?.result?.schoolCycleTypeId ?: -1,
                            shift = it.shift.copy(valueText = state.result?.result?.shift ?: ""),
                            type = it.type.copy(valueText = state.result?.result?.schoolCycleType ?: ""),
                            spinner = state.result?.spinners,
                            read = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            schoolName = "".stringToModelStateOutFieldText(),
                            schoolCycleTypeId = -1,
                            shift = "".stringToModelStateOutFieldText(),
                            type = "".stringToModelStateOutFieldText(),
                            cct = ModelStateOutFieldText(
                                valueText = it.cct.valueText,
                                isError = true,
                                errorMessage = ModelCodeInputs.ET_NOT_FOUND
                            ),
                            read = true
                        )
                    }
                }

            }.onFailure {
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun validateFields() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(dispatcherProvider.io) {
            val cctState = validateFieldsUseCase.validateCctCompose(_uiState.value.cct.valueText)
            val gradeState = validateFieldsUseCase.validateGradeCompose(_uiState.value.grade.valueText)
            val groupState = validateFieldsUseCase.validateGroupCompose(_uiState.value.group.valueText)
            val cycleState = validateFieldsUseCase.validateCycleCompose(_uiState.value.cycle.valueText)

            _uiState.update {
                it.copy(
                    grade = gradeState,
                    group = groupState,
                    cycle = cycleState,
                    cct = cctState
                )
            }

            if (!(cctState.isError || gradeState.isError || groupState.isError || cycleState.isError)) {
                registerOneSchool()
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun registerOneSchool() {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                registerOneSchoolUseCase.registerOneSchool(
                    cct = _uiState.value.cct.valueText,
                    schoolCycleTypeId = _uiState.value.schoolCycleTypeId,
                    grade = _uiState.value.grade.valueText.toInt(),
                    group = _uiState.value.group.valueText,
                    cycle = _uiState.value.cycle.valueText.toInt()
                )
            }.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                }
            }.onFailure {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = false
                    )
                }
            }
        }
    }

    /** Seccion para voz */
    override fun onCleared() {
        super.onCleared()
        voiceRecognitionManager.release()
    }

    fun change() {
        if (isListening) {
            voiceRecognitionManager.startListening()
            isListening = false
            _uiState.update { it.copy(buttonColor = color_error) }
        } else {
            voiceRecognitionManager.stopListening()
            isListening = true
            _uiState.update { it.copy(buttonColor = color_success) }
        }
    }

    private fun validateData(state: List<String>) {
        val result = state.firstOrNull()?.replace(" ", "")?.uppercase()
        onCctChanged(result?: "")
        _uiState.update { it.copy(cct = it.cct.copy( valueText = result?: "")) }
    }
}