package com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.VoiceRecognitionManager
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelVoiceConstants
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.RegisterOneStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ValidateFieldsStudentUseCase
import com.mx.liftechnology.registroeducativo.main.funextensions.log
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterStudentUIState
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_error
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_success
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterStudentViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsStudentUseCase: ValidateFieldsStudentUseCase,
    private val registerOneStudentUseCase: RegisterOneStudentUseCase,
    private val validateVoiceStudentUseCase: ValidateVoiceStudentUseCase,
    private val voiceRecognitionManager: VoiceRecognitionManager
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterStudentUIState())
    val uiState: StateFlow<ModelRegisterStudentUIState> = _uiState.asStateFlow()
    private val myValue: ModelRegisterStudentUIState
        get() = _uiState.value


    init {
        // Observa cambios del reconocimiento de voz
        voiceRecognitionManager.resultsLiveData.observeForever { results ->
            log(results.toString())
            validateDataRecord(results)
        }
    }

    private var isListening = true

    fun onChangeName(name: String) {
        _uiState.update { it.copy(
            name = name,
            isErrorName =  ModelStateOutFieldText(false, "")
        ) }
    }
    fun onChangeLastName(lastName: String) {
        _uiState.update { it.copy(
            lastName = lastName,
            isErrorLastName =  ModelStateOutFieldText(false, "")
        ) }
    }
    fun onChangeSecondLastName(secondLastName: String) {
        _uiState.update { it.copy(
            secondLastName = secondLastName,
            isErrorSecondLastName =  ModelStateOutFieldText(false, "")
        ) }
    }
    fun onChangeCurp(curp: String) {
        _uiState.update { it.copy(
            curp = curp,
            isErrorCurp =  ModelStateOutFieldText(false, "")
        ) }
    }
    fun onChangeBirthday(birthday: String) {
        _uiState.update { it.copy(
            birthday = birthday,
            isErrorBirthday =  ModelStateOutFieldText(false, "")
        ) }
    }
    fun onChangePhoneNUmber(phoneNumber: String) {
        _uiState.update { it.copy(
            phoneNumber = phoneNumber,
            isErrorPhoneNumber =  ModelStateOutFieldText(false, "")
        ) }
    }

    fun validateFieldsCompose() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val nameState = validateFieldsStudentUseCase.validateName(_uiState.value.name)
            val lastNameState = validateFieldsStudentUseCase.validateLastName(_uiState.value.lastName)
            val secondLastNameState =
                validateFieldsStudentUseCase.validateSecondLastName(_uiState.value.secondLastName)
            val curpState = validateFieldsStudentUseCase.validateCurp(_uiState.value.curp)
            val birthdayState = validateFieldsStudentUseCase.validateBirthday(_uiState.value.birthday)
            val phoneNumberState = validateFieldsStudentUseCase.validatePhoneNumber(_uiState.value.phoneNumber)

            _uiState.update {
                it.copy(
                    isErrorName = nameState,
                    isErrorLastName = lastNameState,
                    isErrorSecondLastName = secondLastNameState,
                    isErrorCurp = curpState,
                    isErrorBirthday = birthdayState,
                    isErrorPhoneNumber = phoneNumberState
                )
            }

            if (!(nameState.isError || lastNameState.isError || secondLastNameState.isError || curpState.isError
                        || birthdayState.isError || phoneNumberState.isError
            )) {
                registerOneStudentCompose()
            }else{
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun registerOneStudentCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                registerOneStudentUseCase.registerOneStudent(
                    myValue.name,
                    myValue.lastName,
                    myValue.secondLastName,
                    myValue.curp,
                    myValue.birthday,
                    myValue.phoneNumber
                )
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

    fun getArguments(student: ModelStudentDomain) {
        _uiState.update {
            it.copy(
                name = student.name ?: "",
                lastName = student.lastName ?: "",
                secondLastName = student.secondLastName ?: "",
                curp = student.curp ?: "",
                birthday = student.birthday ?: "",
                phoneNumber = student.phoneNumber ?: "",
            )
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

    private fun validateDataRecord(data: List<String>) {
        viewModelScope.launch {
            val result = validateVoiceStudentUseCase.buildModelStudent(data.firstOrNull())
            result?.let { studentData ->
                log(studentData.toString())
                _uiState.update { currentState ->
                    currentState.copy(
                        name = studentData[ModelVoiceConstants.NAME] ?: "",
                        lastName = studentData[ModelVoiceConstants.LAST_NAME] ?: "",
                        secondLastName = studentData[ModelVoiceConstants.SECOND_LAST_NAME] ?: "",
                        curp = studentData[ModelVoiceConstants.CURP] ?: "",
                        birthday = studentData[ModelVoiceConstants.BIRTHDAY] ?: "",
                        phoneNumber = studentData[ModelVoiceConstants.PHONE_NUMBER] ?: ""
                    )
                }
            }
        }
    }
}