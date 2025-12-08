package com.mx.liftechnology.registroeducativo.main.ui.formativeFields.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.SpinnersWorkMethodsDomain
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.formativeField.GetListWorkTypeUseCase
import com.mx.liftechnology.domain.usecase.formativeField.RegisterFormativeFieldsWithValidationUseCase
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.RegisterFormativeFieldUiState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel para la pantalla de registro de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterFormativeFieldsViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val registerFormativeFieldsWithValidationUseCase: RegisterFormativeFieldsWithValidationUseCase,
    private val getListWorkTypeUseCase: GetListWorkTypeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterFormativeFieldUiState())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<RegisterFormativeFieldUiState> = _uiState.asStateFlow()

    /**
     * Se llama cuando cambia el nombre de la materia.
     *
     * @param formativeField El nuevo nombre de la materia.
     */
    fun onFormativeFieldChanged(formativeField: ModelStateOutFieldText) {
        _uiState.update { it.copy(formativeField = formativeField) }
    }

    /**
     * Se llama cuando cambia el nombre de un método de trabajo.
     *
     * @param value Un par que contiene el nuevo tipo de trabajo y el índice del elemento que cambió.
     */
    fun onNameChange(value: Pair<WorkTypeDomain?, Int>) {
        _uiState.update {
            it.copy(
                listAdapter = it.listAdapter?.map { formativeField ->
                    if (formativeField.position == value.second) {
                        formativeField.copy(
                            name = value.first?.name.stringToModelStateOutFieldText(),
                            workTypeId = value.first?.workTypeId,
                        )
                    } else {
                        formativeField
                    }
                },
            )
        }
    }

    /**
     * Se llama cuando cambia el porcentaje de un método de trabajo.
     *
     * @param value Un par que contiene el nuevo porcentaje y el índice del elemento que cambió.
     */
    fun onPercentChange(value: Pair<ModelStateOutFieldText, Int>) {
        _uiState.update {
            it.copy(
                listAdapter = it.listAdapter?.map { formativeField ->
                    if (formativeField.position == value.second) {
                        formativeField.copy(percent = value.first)
                    } else {
                        formativeField
                    }
                },
            )
        }
    }

    /**
     * Se llama cuando cambia el número de opciones.
     *
     * @param options El nuevo número de opciones.
     */
    fun onOptionsChanged(options: String) {
        if (options.toIntOrNull() != null && options.toInt() > 0) {
            val list = MutableList(options.toInt()) { index ->
                SpinnersWorkMethodsDomain(
                    position = index,
                    name = "".stringToModelStateOutFieldText(),
                    percent = "".stringToModelStateOutFieldText(),
                    workTypeId = 0,
                )
            }

            _uiState.update {
                it.copy(
                    options = options.stringToModelStateOutFieldText(),
                    listAdapter = list,
                    formativeField = it.formativeField.valueText.stringToModelStateOutFieldText()
                )
            }
        }
    }

    /**
     * Valida los campos de entrada y procede a registrar la materia si son válidos.
     * La lógica de validación + operación está encapsulada en el Use Case.
     */
    fun validateFieldsCompose() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            
            // El Use Case combina validación + operación
            val result = withContext(dispatcherProvider.io) {
                registerFormativeFieldsWithValidationUseCase.invoke(
                    formativeField = _uiState.value.formativeField.valueText,
                    options = _uiState.value.options.valueText,
                    listAdapter = _uiState.value.listAdapter?.toMutableList()
                )
            }

            val validationResult = result.validationResult

            // Actualizar los estados de validación de los campos
            _uiState.update {
                it.copy(
                    formativeField = validationResult.validationStates["formativeField"] ?: it.formativeField,
                    options = validationResult.validationStates["options"] ?: it.options,
                    listAdapter = result.updatedListAdapter
                )
            }

            // Si las validaciones pasaron, manejar el resultado de la operación
            if (validationResult.isValid && validationResult.operationResult != null) {
                when (val operationResult = validationResult.operationResult) {
                    is SuccessResult -> {
                        _uiState.update { it.copy(
                            uiState = ModelStateUIEnum.SUCCESS,
                            controlToast = ToastUiState(
                                messageToast = R.string.toast_success_register_formative_field,
                                showToast = true,
                                typeToast = ModelStateTypeToastUI.SUCCESS
                            )
                        ) }
                    }
                    is ErrorResult -> {
                        val userError = ErrorMapper.mapErrorToUI(operationResult.error)
                        val messageRes = ErrorToMessageMapper.mapErrorToMessage(
                            error = userError,
                            context = ErrorToMessageMapper.ErrorContext.REGISTER_FORMATIVE_FIELD
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
     * Gets the list of workType types.
     */
    fun getListWorkType() {
        viewModelScope.launch {
            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                getListWorkTypeUseCase.invoke()
            }

            when (result) {
                is SuccessResult -> {
                    _uiState.update {
                        it.copy(listWorkMethods = result.data)
                    }
                }

                else -> {
                    _uiState.update {
                        it.copy(
                            listWorkMethods = listOf(
                                WorkTypeDomain(
                                    workTypeId = -1,
                                    name = "Nuevo",
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
        
        _uiState.update {
            it.copy(
                controlToast = it.controlToast.copy(showToast = show)
            )
        }
    }
}