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
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterSchoolUIState
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_error
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_success
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterSchoolViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getCctUseCase: GetCctUseCase,
    private val validateFieldsUseCase: ValidateFieldsRegisterSchoolUseCase,
    private val registerOneSchoolUseCase: RegisterOneSchoolUseCase,
    private val voiceRecognitionManager: VoiceRecognitionManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterSchoolUIState())
    val uiState: StateFlow<ModelRegisterSchoolUIState> = _uiState.asStateFlow()

    init {
        // Observa cambios del reconocimiento de voz
        voiceRecognitionManager.resultsLiveData.observeForever { results ->
            validateData(results)
        }
    }

    private var isListening = true

    fun onCycleChanged(cycle: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _uiState.update {
                it.copy(
                    cycle = cycle.stringToModelStateOutFieldText()
                )
            }
        }
    }

    fun onGradeChanged(grade: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _uiState.update {
                it.copy(
                    grade = grade.stringToModelStateOutFieldText()
                )
            }
        }
    }

    fun onGroupChanged(group: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _uiState.update {
                it.copy(
                    group = group.stringToModelStateOutFieldText()
                )
            }
        }
    }

    fun onCctChanged(cct: String) {
        viewModelScope.launch (dispatcherProvider.io){
            _uiState.update {
                if (cct.length == 10) {
                    getSchoolCCT(cct)
                    it.copy(
                        uiState = ModelStateUIEnum.LOADING,
                        cct = cct.uppercase().stringToModelStateOutFieldText()
                    )
                } else {
                    it.copy(
                        cct = cct.uppercase()
                            .stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_NOT_FOUND),
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
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.NOTHING,
                        schoolName = it.schoolName.copy(
                            valueText = state.result?.result?.schoolName ?: ""
                        ),
                        schoolCycleTypeId = state.result?.result?.schoolCycleTypeId ?: -1,
                        shift = it.shift.copy(valueText = state.result?.result?.shift ?: ""),
                        type = it.type.copy(
                            valueText = state.result?.result?.schoolCycleType ?: ""
                        ),
                        spinner = state.result?.spinners,
                        read = false
                    )
                }
            }

            is ErrorUserState -> {
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.NOTHING,
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
            _uiState.update { it.copy( uiState = ModelStateUIEnum.LOADING) }

            val cctState = validateFieldsUseCase.validateCctCompose(_uiState.value.cct.valueText)
            val gradeState =
                validateFieldsUseCase.validateGradeCompose(_uiState.value.grade.valueText)
            val groupState =
                validateFieldsUseCase.validateGroupCompose(_uiState.value.group.valueText)
            val cycleState =
                validateFieldsUseCase.validateCycleCompose(_uiState.value.cycle.valueText)

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
                _uiState.update { it.copy( uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    private suspend fun registerOneSchool() {
        when (val result = registerOneSchoolUseCase.invoke(
            cct = _uiState.value.cct.valueText,
            schoolCycleTypeId = _uiState.value.schoolCycleTypeId,
            grade = _uiState.value.grade.valueText.toInt(),
            group = _uiState.value.group.valueText,
            cycle = _uiState.value.cycle.valueText.toInt()
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
        viewModelScope.launch (dispatcherProvider.main){
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
            _uiState.update { it.copy(cct = it.cct.copy(valueText = result ?: "")) }
        }
    }

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