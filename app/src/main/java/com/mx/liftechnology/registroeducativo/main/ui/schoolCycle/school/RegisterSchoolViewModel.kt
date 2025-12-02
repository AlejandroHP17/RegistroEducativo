/**
 * @file Define el ViewModel para la pantalla de registro de una escuela.
 * @author PelkiDev
 * @version 1.0.0
 */
package com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.school

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.voice.VoiceRecognitionManager
import com.mx.liftechnology.core.util.extension.logDebug
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.schoolCycle.school.GetCctUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.school.RegisterSchoolWithValidationUseCase
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.SchoolCycleMapper.toUi
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.events.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.RegisterSchoolUiInputs
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.RegisterSchoolUiSemiAutomaticData
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.RegisterSchoolUiState
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorError
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import com.mx.liftechnology.registroeducativo.main.util.getPeriodsByType
import com.mx.liftechnology.registroeducativo.main.util.navigation.AppRoutes
import com.mx.liftechnology.registroeducativo.main.util.toSelectPeriod
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel para la pantalla de Registro de Escuela.
 * Gestiona el estado de la UI, la lógica de validación y la comunicación con los casos de uso
 * para buscar una CCT (Clave de Centro de Trabajo) y registrar una nueva escuela.
 *
 * @property dispatcherProvider Proveedor de dispatchers para controlar los hilos de ejecución.
 * @property getCctUseCase Caso de uso para obtener la información de una escuela a partir de su CCT.
 * @property validateFieldsUseCase Caso de uso para validar los campos del formulario.
 * @property registerCycleSchoolUseCase Caso de uso para registrar la nueva escuela.
 * @property voiceRecognitionManager Gestor para el reconocimiento de voz.
 * @author PelkiDev
 * @version 1.0.0
 */
class RegisterSchoolViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getCctUseCase: GetCctUseCase,
    private val registerSchoolWithValidationUseCase: RegisterSchoolWithValidationUseCase,
    private val voiceRecognitionManager: VoiceRecognitionManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterSchoolUiState())
    /** Estado de la UI que contiene eventos generales como carga, éxito o error. */
    val uiState: StateFlow<RegisterSchoolUiState> = _uiState.asStateFlow()

    private val _uiSemiAutomaticData = MutableStateFlow(RegisterSchoolUiSemiAutomaticData())
    /** Estado que almacena los datos de la escuela que se rellenan automáticamente tras una búsqueda de CCT exitosa. */
    val uiSemiAutomaticData: StateFlow<RegisterSchoolUiSemiAutomaticData> = _uiSemiAutomaticData.asStateFlow()

    private val _inputState = MutableStateFlow(RegisterSchoolUiInputs())
    /** Estado que contiene los valores de los campos de entrada del usuario (CCT, grado, grupo, ciclo). */
    val inputState: StateFlow<RegisterSchoolUiInputs> = _inputState.asStateFlow()
    private val inputStateVM: RegisterSchoolUiInputs get() = _inputState.value

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    /** Eventos de UI que deben ser manejados una sola vez (navegación, etc.) */
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private var isListening = true

    init {
        // Observa los resultados del reconocimiento de voz usando StateFlow
        voiceRecognitionManager.resultsStateFlow
            .onEach { results ->
                logDebug(results.toString())
                validateData(results)
            }
            .launchIn(viewModelScope)
    }

    /**
     * Se invoca cuando el valor del spinner de ciclo cambia.
     * Actualiza el estado del campo de ciclo en [inputState].
     *
     * @param type El nuevo valor del ciclo seleccionado.
     * @author PelkiDev
     * @version 1.0.0
     */
    fun onTypeChanged(type: ModelCustomSpinner) {
        // Actualizaciones de estado simples no necesitan corrutinas
        val data = _uiSemiAutomaticData.value.periodCatalog
            ?.getPeriodsByType(type.value.toString())

        // Actualiza el campo cycle al primer elemento de la lista del nuevo tipo
        val firstCycle = data?.firstOrNull()
        
        _uiSemiAutomaticData.update { current ->
            current.copy(
                spinner = current.spinner?.copy(
                    cycle = data
                )
            )
        }
        
        _inputState.update {
            it.copy(
                type = type.value.stringToModelStateOutFieldText(),
                cycle = firstCycle?.value?.stringToModelStateOutFieldText() 
                    ?: ModelStateOutFieldText() // Si no hay ciclos, deja el campo vacío
            )
        }
    }

    /**
     * Se invoca cuando el valor del spinner de ciclo cambia.
     * Actualiza el estado del campo de ciclo en [inputState].
     *
     * @param cycle El nuevo valor del ciclo seleccionado.
     * @author PelkiDev
     * @version 1.0.0
     */
    fun onCycleChanged(cycle: ModelCustomSpinner) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _inputState.update {
            it.copy(cycle = cycle.value.stringToModelStateOutFieldText())
        }
    }

    /**
     * Se invoca cuando el valor del spinner de grado cambia.
     * Actualiza el estado del campo de grado en [inputState].
     *
     * @param grade El nuevo valor del grado seleccionado.
     * @author PelkiDev
     * @version 1.0.0
     */
    fun onGradeChanged(grade: ModelCustomSpinner) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _inputState.update {
            it.copy(grade = grade.value.stringToModelStateOutFieldText())
        }
    }

    /**
     * Se invoca cuando el valor del spinner de grupo cambia.
     * Actualiza el estado del campo de grupo en [inputState].
     *
     * @param group El nuevo valor del grupo seleccionado.
     * @author PelkiDev
     * @version 1.0.0
     */
    fun onGroupChanged(group: ModelCustomSpinner) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _inputState.update {
            it.copy(group = group.value.stringToModelStateOutFieldText())
        }
    }

    /**
     * Se invoca cuando el valor del campo de texto de la CCT cambia.
     * Si la CCT tiene 10 caracteres, inicia el proceso de carga y búsqueda de la escuela.
     * Si no, limpia los campos de la escuela y actualiza el estado del campo de CCT.
     *
     * @param cct El nuevo valor de la CCT introducido por el usuario.
     * @author PelkiDev
     * @version 1.0.0
     */
    fun onCctChanged(cct: ModelStateOutFieldText) {
        if (cct.valueText.length == 10) {
            _uiState.update {
                it.copy(uiState = ModelStateUIEnum.LOADING)
            }
            _inputState.update {
                it.copy(cct = cct)
            }
            viewModelScope.launch {
                getSchoolCCT(cct.valueText)
            }
        } else {
            _inputState.update {
                it.copy(
                    cct = cct.valueText.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_NOT_FOUND)
                )
            }
            _uiSemiAutomaticData.update {
                it.copy(
                    schoolName = it.schoolName.copy(valueText = ""),
                    shiftName = it.shiftName.copy(valueText = ""),
                    read = true
                )
            }
        }
    }

    fun onLabelCycleChanged(labelCycle: ModelStateOutFieldText) {
        // Actualizaciones de estado simples no necesitan corrutinas
        _inputState.update { it.copy(labelCycle = labelCycle) }
    }

    private suspend fun getSchoolCCT(cct: String) {
        // Las operaciones de red deben ejecutarse en el dispatcher de I/O
        val state = withContext(dispatcherProvider.io) {
            getCctUseCase.invoke(cct)
        }

        when (state) {
            is SuccessResult -> {
                _uiState.update {
                    it.copy(uiState = ModelStateUIEnum.NOTHING)
                }
                _uiSemiAutomaticData.update {
                    it.copy(
                        schoolName = it.schoolName.copy(valueText = state.data.result.schoolName),
                        schoolId = state.data.result.id,
                        shiftName = it.shiftName.copy(valueText = state.data.result.shiftName ?: ""),
                        spinner = state.data.spinners.toUi(),
                        read = false,
                        periodCatalog = state.data.result.periodCatalog
                    )

                }
            }
            else -> {
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.NOTHING,
                    )
                }
                _uiSemiAutomaticData.update {
                    it.copy(
                        schoolName = "".stringToModelStateOutFieldText(),
                        shiftName = "".stringToModelStateOutFieldText(),
                        read = true
                    )
                }
                _inputState.update {
                    it.copy(
                        cct = ModelStateOutFieldText(
                            valueText = it.cct.valueText,
                            isError = true,
                            errorMessage = ModelCodeInputs.ET_NOT_FOUND
                        )
                    )
                }
            }
        }
    }

    /**
     * Inicia la validación de todos los campos de entrada del formulario.
     * Actualiza el estado de los campos con los resultados de la validación y, si todos son válidos,
     * procede a registrar la escuela.
     * La lógica de validación + operación está encapsulada en el Use Case.
     *
     * @author PelkiDev
     * @version 1.0.0
     */
    fun validateFields() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            // Calcular el period antes de llamar al Use Case
            val period = _uiSemiAutomaticData.value.periodCatalog.toSelectPeriod(
                inputStateVM.cycle.valueText,
                inputStateVM.type.valueText
            )

            // El Use Case combina validación + operación
            val validationResult = withContext(dispatcherProvider.io) {
                registerSchoolWithValidationUseCase.invoke(
                    cct = inputStateVM.cct.valueText,
                    type = inputStateVM.type.valueText,
                    grade = inputStateVM.grade.valueText,
                    group = inputStateVM.group.valueText,
                    cycle = inputStateVM.cycle.valueText,
                    labelCycle = inputStateVM.labelCycle.valueText,
                    schoolId = _uiSemiAutomaticData.value.schoolId,
                    periodCatalogId = period,
                    shiftName = _uiSemiAutomaticData.value.shiftName.valueText
                )
            }

            // Actualizar los estados de validación de los campos
            _inputState.update { 
                it.copy(
                    cct = validationResult.validationStates["cct"] ?: it.cct,
                    type = validationResult.validationStates["type"] ?: it.type,
                    grade = validationResult.validationStates["grade"] ?: it.grade,
                    group = validationResult.validationStates["group"] ?: it.group,
                    cycle = validationResult.validationStates["cycle"] ?: it.cycle,
                    labelCycle = validationResult.validationStates["labelCycle"] ?: it.labelCycle
                )
            }

            // Si las validaciones pasaron, manejar el resultado de la operación
            if (validationResult.isValid && validationResult.operationResult != null) {
                when (val operationResult = validationResult.operationResult) {
                    is SuccessResult -> {
                        _uiState.update {
                            it.copy(
                                uiState = ModelStateUIEnum.SUCCESS,
                                controlToast = ToastUiState(
                                    messageToast = R.string.toast_success_register_school,
                                    showToast = true,
                                    typeToast = ModelStateTypeToastUI.SUCCESS
                                )
                            )
                        }
                        
                        _uiEvent.emit(UiEvent.Navigate(AppRoutes.Main.menuWithReload(true)))
                    }

                    is ErrorResult -> {
                        val userError = ErrorMapper.mapErrorToUI(operationResult.error)
                        val messageRes = ErrorToMessageMapper.mapErrorToMessage(
                            error = userError,
                            context = ErrorToMessageMapper.ErrorContext.REGISTER_SCHOOL
                        )

                        _uiState.update {
                            it.copy(
                                uiState = ModelStateUIEnum.ERROR,
                                controlToast = messageRes?.let { msg ->
                                    ToastUiState(
                                        messageToast = msg,
                                        showToast = true,
                                        typeToast = ModelStateTypeToastUI.ERROR
                                    )
                                } ?: it.controlToast.copy(showToast = false)
                            )
                        }
                    }
                    else -> {}
                }
            } else {
                
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    /**
     * Se llama cuando el ViewModel está a punto de ser destruido.
     * Libera los recursos utilizados por el [voiceRecognitionManager] para evitar fugas de memoria.
     *
     * @author PelkiDev
     * @version 1.0.0
     */
    override fun onCleared() {
        super.onCleared()
        voiceRecognitionManager.release()
    }

    /**
     * Activa o desactiva el reconocimiento de voz.
     * Cambia el color del botón para dar feedback visual al usuario sobre el estado de la escucha.
     *
     * @author PelkiDev
     * @version 1.0.0
     */
    fun change() {
        viewModelScope.launch {
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
            onCctChanged((result ?: "").stringToModelStateOutFieldText())
            _inputState.update { 
                it.copy(cct = result?.uppercase()?.stringToModelStateOutFieldText() ?: ModelStateOutFieldText())
            }
        }
    }

    /**
     * Modifica la visibilidad del mensaje toast en la UI.
     *
     * @param show `true` para mostrar el toast, `false` para ocultarlo.
     * @author PelkiDev
     * @version 1.0.0
     */
    fun modifyShowToast(show: Boolean) {
        
        _uiState.update {
            it.copy(
                controlToast = it.controlToast.copy(showToast = show)
            )
        }
    }
}