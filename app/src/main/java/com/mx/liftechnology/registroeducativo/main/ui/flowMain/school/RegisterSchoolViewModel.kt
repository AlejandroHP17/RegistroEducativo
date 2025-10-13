package com.mx.liftechnology.registroeducativo.main.ui.flowMain.school

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
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterSchoolUISemiAutomaticData
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterSchoolUIState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.toUi
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorError
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for the School Registration screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterSchoolViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getCctUseCase: GetCctUseCase,
    private val validateFieldsUseCase: ValidateFieldsRegisterSchoolUseCase,
    private val registerOneSchoolUseCase: RegisterOneSchoolUseCase,
    private val voiceRecognitionManager: VoiceRecognitionManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterSchoolUIState())
    /** The UI state for the screen. */
    val uiState: StateFlow<ModelRegisterSchoolUIState> = _uiState.asStateFlow()

    private val _uiSemiAutomaticData = MutableStateFlow(ModelRegisterSchoolUISemiAutomaticData())
    /** The data state for semi-automatic fields. */
    val uiSemiAutomaticData: StateFlow<ModelRegisterSchoolUISemiAutomaticData> = _uiSemiAutomaticData.asStateFlow()

    private val _cct = MutableStateFlow(ModelStateOutFieldText())
    /** The state of the CCT input field. */
    val cct: StateFlow<ModelStateOutFieldText> = _cct.asStateFlow()

    private val _grade = MutableStateFlow(ModelStateOutFieldText())
    /** The state of the grade input field. */
    val grade: StateFlow<ModelStateOutFieldText> = _grade.asStateFlow()

    private val _group = MutableStateFlow(ModelStateOutFieldText())
    /** The state of the group input field. */
    val group: StateFlow<ModelStateOutFieldText> = _group.asStateFlow()

    private val _cycle = MutableStateFlow(ModelStateOutFieldText())
    /** The state of the cycle input field. */
    val cycle: StateFlow<ModelStateOutFieldText> = _cycle.asStateFlow()

    private val resultsObserver = androidx.lifecycle.Observer<List<String>> { results ->
        logs(results.toString())
        validateData(results)
    }

    private var isListening = true

    /**
     * Called when the cycle input changes.
     *
     * @param cycle The new cycle value.
     */
    fun onCycleChanged(cycle: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _cycle.update { cycle.stringToModelStateOutFieldText() }
        }
    }

    /**
     * Called when the grade input changes.
     *
     * @param grade The new grade value.
     */
    fun onGradeChanged(grade: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _grade.update { grade.stringToModelStateOutFieldText() }
        }
    }

    /**
     * Called when the group input changes.
     *
     * @param group The new group value.
     */
    fun onGroupChanged(group: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _group.update { group.stringToModelStateOutFieldText() }
        }
    }

    /**
     * Called when the CCT input changes.
     *
     * @param cct The new CCT value.
     */
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
                            spinner = state.result?.spinners?.toUi(),
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

    /**
     * Validates the input fields and proceeds to register the school if they are valid.
     */
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

    /**
     * Called when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        voiceRecognitionManager.resultsLiveData.removeObserver(resultsObserver)
        voiceRecognitionManager.release()
    }

    /**
     * Toggles the voice recognition listening state.
     */
    fun change() {
        viewModelScope.launch(dispatcherProvider.main) {
            if (isListening) {
                voiceRecognitionManager.startListening()
                isListening = false
                _uiState.update { it.copy(buttonColor = colorError) }
            } else {
                voiceRecognitionManager.stopListening()
                isListening = true
                _uiState.update { it.copy(buttonColor = colorSuccess) }
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

    /**
     * Modifies the visibility of the toast message.
     *
     * @param show True to show the toast, false to hide it.
     */
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