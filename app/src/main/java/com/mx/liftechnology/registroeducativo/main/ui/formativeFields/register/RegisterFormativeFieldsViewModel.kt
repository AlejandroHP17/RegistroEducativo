package com.mx.liftechnology.registroeducativo.main.ui.formativeFields.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.data.util.UserError
import com.mx.liftechnology.domain.model.formativeFields.ModelSpinnersWorkMethods
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.formativeField.GetListWorkTypeUseCase
import com.mx.liftechnology.domain.usecase.formativeField.RegisterFormativeFieldsBulkUseCase
import com.mx.liftechnology.domain.usecase.formativeField.ValidateFieldsFormativeFieldsUseCase
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.RegisterSubjectUiState
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
class RegisterFormativeFieldsViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsFormativeFieldsUseCase: ValidateFieldsFormativeFieldsUseCase,
    private val registerFormativeFieldsBulkUseCase: RegisterFormativeFieldsBulkUseCase,
    private val getListWorkTypeUseCase: GetListWorkTypeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterSubjectUiState())
    /** El estado de la UI que contiene eventos de la pantalla como carga, éxito o error. */
    val uiState: StateFlow<RegisterSubjectUiState> = _uiState.asStateFlow()

    /**
     * Called when the subject name changes.
     *
     * @param subject The new subject name.
     */
    fun onSubjectChanged(subject: ModelStateOutFieldText) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    subject = subject
                )
            }
        }
    }

    /**
     * Called when the name of a work method changes.
     *
     * @param value A pair containing the new workType type and the index of the item that changed.
     */
    fun onNameChange(value: Pair<ModelWorkTypeData?, Int>) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    listAdapter = it.listAdapter?.map { subject ->
                        if (subject.position == value.second) {
                            subject.copy(
                                name = value.first?.name.stringToModelStateOutFieldText(),
                                workTypeId = value.first?.workTypeId,
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
    fun onPercentChange(value: Pair<ModelStateOutFieldText, Int>) {
        viewModelScope.launch(dispatcherProvider.io) {
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
                        workTypeId = 0,
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
                validateFieldsFormativeFieldsUseCase.validateNameCompose(_uiState.value.subject.valueText)
            val optionState =
                validateFieldsFormativeFieldsUseCase.validateOptionCompose(_uiState.value.options.valueText)
            val updatedListState =
                validateFieldsFormativeFieldsUseCase.validateListJobsCompose(_uiState.value.listAdapter?.toMutableList())

            _uiState.update {
                it.copy(
                    subject = nameState,
                    options = optionState,
                    listAdapter = updatedListState
                )
            }

            if (!(nameState.isError || optionState.isError)) {
                val optionState = validateFieldsFormativeFieldsUseCase.validPercentCompose(_uiState.value.listAdapter?.toMutableList())

                _uiState.update { it.copy(options = optionState) }

                if (!optionState.isError) {
                    registerFormativeField()
                }else{
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                }
            } else {
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    private suspend fun registerFormativeField() {
        when (val result =
            registerFormativeFieldsBulkUseCase.invoke(
                _uiState.value.listAdapter?.toMutableList(),
                _uiState.value.subject.valueText
            )){

            is SuccessResult -> {
                _uiState.update { it.copy(
                    uiState = ModelStateUIEnum.SUCCESS,
                    controlToast = ToastUiState(
                        messageToast = R.string.toast_success_register_subject,
                        showToast = true,
                        typeToast = ModelStateTypeToastUI.SUCCESS
                    )
                ) }
            }
            is ErrorResult -> {
                val msg = when(ErrorMapper.mapErrorToUI(result.error)){
                    UserError.SHOW_GENERIC_ERROR -> R.string.toast_error_register_subject
                    else -> null
                }

                if(msg != null){
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR,
                            controlToast = ToastUiState(
                                messageToast = msg,
                                showToast = true,
                                typeToast = ModelStateTypeToastUI.ERROR
                            )
                        )
                    }
                }else{
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.ERROR) }
                }
            }
        }
    }

    /**
     * Gets the list of workType types.
     */
    fun getListWorkType() {
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = getListWorkTypeUseCase.invoke()
            ) {
                is SuccessResult -> {
                    _uiState.update {
                        it.copy(
                            listWorkMethods = result.data
                        )
                    }
                }

                else -> {
                    _uiState.update {
                        it.copy(
                            listWorkMethods = listOf(
                                ModelWorkTypeData(
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
        viewModelScope.launch (dispatcherProvider.main){
            _uiState.update {
                it.copy(
                    controlToast = ToastUiState(
                        messageToast = it.controlToast.messageToast,
                        showToast = show,
                        typeToast = it.controlToast.typeToast
                    )
                )
            }
        }
    }
}