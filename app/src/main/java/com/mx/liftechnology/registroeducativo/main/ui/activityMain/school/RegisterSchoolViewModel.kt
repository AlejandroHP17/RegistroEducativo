package com.mx.liftechnology.registroeducativo.main.ui.activityMain.school

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.VoiceRecognitionManager
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.GetCctUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.RegisterOneSchoolUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.ValidateFieldsRegisterSchoolUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterSchoolUISemiAutomaticData
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterSchoolUIState
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_error
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_success
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterSchoolViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getCctUseCase: GetCctUseCase,
    private val validateFieldsUseCase: ValidateFieldsRegisterSchoolUseCase,
    private val registerOneSchoolUseCase: RegisterOneSchoolUseCase,
    private val voiceRecognitionManager: VoiceRecognitionManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterSchoolUIState())
    val uiState: StateFlow<ModelRegisterSchoolUIState> = _uiState.asStateFlow()

    private val _uiSemiAutomaticData = MutableStateFlow(ModelRegisterSchoolUISemiAutomaticData())
    val uiSemiAutomaticData: StateFlow<ModelRegisterSchoolUISemiAutomaticData> = _uiSemiAutomaticData.asStateFlow()

    private val _cct = MutableStateFlow(ModelStateOutFieldText())
    val cct: StateFlow<ModelStateOutFieldText> = _cct.asStateFlow()

    private val _grade = MutableStateFlow(ModelStateOutFieldText())
    val grade: StateFlow<ModelStateOutFieldText> = _grade.asStateFlow()

    private val _group = MutableStateFlow(ModelStateOutFieldText())
    val group: StateFlow<ModelStateOutFieldText> = _group.asStateFlow()

    private val _cycle = MutableStateFlow(ModelStateOutFieldText())
    val cycle: StateFlow<ModelStateOutFieldText> = _cycle.asStateFlow()

    init {
        // Observa cambios del reconocimiento de voz
        voiceRecognitionManager.resultsLiveData.observeForever { results ->
            validateData(results)
        }
    }

    private var isListening = true

    fun onCycleChanged(cycle: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _cycle.update { cycle.stringToModelStateOutFieldText() }
        }
    }

    fun onGradeChanged(grade: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _grade.update { grade.stringToModelStateOutFieldText() }
        }
    }

    fun onGroupChanged(group: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _group.update { group.stringToModelStateOutFieldText() }
        }
    }

    fun onCctChanged(cct: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            if (cct.length == 10) {
                _uiState.update {
                    it.copy(uiState = ModelStateUIEnum.LOADING)
                }
                _cct.update { cct.uppercase().stringToModelStateOutFieldText() }
                getSchoolCCT(cct.uppercase())
            } else {
                _cct.update {
                    cct.uppercase()
                        .stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_NOT_FOUND)
                }
                _uiSemiAutomaticData.update {
                    it.copy(
                        schoolName = it.schoolName.copy(valueText = ""),
                        shift = it.shift.copy(valueText = ""),
                        type = it.type.copy(valueText = ""),
                        read = true
                    )
                }
            }
        }
    }

    /** Go to validate  the cct
     * @author pelkidev
     * @since 1.0.0
     * */
    private suspend fun getSchoolCCT(cct: String) {
        when (val state = getCctUseCase.invoke(cct)) {
            is SuccessState -> {
                withContext(dispatcherProvider.main){
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.NOTHING
                        )
                    }
                    _uiSemiAutomaticData.update {
                        it.copy(
                            schoolName = it.schoolName.copy(valueText = state.result?.result?.schoolName ?: ""),
                            shift = it.shift.copy(valueText = state.result?.result?.shift ?: ""),
                            type = it.type.copy(valueText = state.result?.result?.schoolCycleType ?: ""),
                            schoolCycleTypeId = state.result?.result?.schoolCycleTypeId ?: -1,
                            spinner = state.result?.spinners,
                            read = false
                        )
                    }
                }
            }

            is ErrorUserState -> {
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.NOTHING,
                    )
                }
                _uiSemiAutomaticData.update {
                    it.copy(
                        schoolName = "".stringToModelStateOutFieldText(),
                        shift = "".stringToModelStateOutFieldText(),
                        type = "".stringToModelStateOutFieldText(),
                        schoolCycleTypeId = -1,
                        read = true
                    )
                }
                _cct.update {
                    ModelStateOutFieldText(
                        valueText = it.valueText,
                        isError = true,
                        errorMessage = ModelCodeInputs.ET_NOT_FOUND
                    )
                }
            }

            else -> {
                logs(state.toString())
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.ERROR
                    )
                }
            }
        }
    }

    fun validateFields() {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            val cctState = validateFieldsUseCase.validateCctCompose(_cct.value.valueText)
            val gradeState = validateFieldsUseCase.validateGradeCompose(_grade.value.valueText)
            val groupState = validateFieldsUseCase.validateGroupCompose(_group.value.valueText)
            val cycleState = validateFieldsUseCase.validateCycleCompose(_cycle.value.valueText)

            _grade.update { gradeState }
            _group.update { groupState }
            _cycle.update { cycleState }
            _cct.update { cctState }


            if (!(cctState.isError || gradeState.isError || groupState.isError || cycleState.isError)) {
                registerOneSchool()
            } else {
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    private suspend fun registerOneSchool() {
        when (val result = registerOneSchoolUseCase.invoke(
            cct = _cct.value.valueText,
            schoolCycleTypeId = _uiSemiAutomaticData.value.schoolCycleTypeId,
            grade = _grade.value.valueText.toInt(),
            group = _group.value.valueText,
            cycle = _cycle.value.valueText.toInt()
        )) {
            is SuccessState -> {
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.SUCCESS,
                        controlToast = ModelStateToastUI(
                            messageToast = R.string.toast_success_register_school,
                            showToast = true,
                            typeToast = ModelStateTypeToastUI.SUCCESS
                        )
                    )
                }
            }

            is ErrorUserState -> {
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.ERROR,
                        controlToast = ModelStateToastUI(
                            messageToast = R.string.toast_error_register_school,
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

    /** Seccion para voz */
    override fun onCleared() {
        super.onCleared()
        voiceRecognitionManager.release()
    }

    fun change() {
        viewModelScope.launch(dispatcherProvider.main) {
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
    }

    private fun validateData(state: List<String>) {
        viewModelScope.launch {
            val result = state.firstOrNull()?.replace(" ", "")?.uppercase()
            onCctChanged(result ?: "")
            _cct.update { it.copy(valueText = result ?: "") }
        }
    }

    fun modifyShowToast(show: Boolean) {
        viewModelScope.launch(dispatcherProvider.main) {
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