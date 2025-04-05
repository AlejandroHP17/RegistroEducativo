package com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.VoiceRecognitionManager
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
import com.mx.liftechnology.registroeducativo.main.viewextensions.stringToModelStateOutFieldText
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
            name = name.stringToModelStateOutFieldText()
        ) }
    }
    fun onChangeLastName(lastName: String) {
        _uiState.update { it.copy(
            lastName = lastName.stringToModelStateOutFieldText()
        ) }
    }
    fun onChangeSecondLastName(secondLastName: String) {
        _uiState.update { it.copy(
            secondLastName = secondLastName.stringToModelStateOutFieldText()
        ) }
    }
    fun onChangeCurp(curp: String) {
        _uiState.update { it.copy(
            curp = curp.stringToModelStateOutFieldText()
        ) }
    }
    fun onChangeBirthday(birthday: String) {
        _uiState.update { it.copy(
            birthday = birthday.stringToModelStateOutFieldText()
        ) }
    }
    fun onChangePhoneNUmber(phoneNumber: String) {
        _uiState.update { it.copy(
            phoneNumber = phoneNumber.stringToModelStateOutFieldText()
        ) }
    }

    fun validateFieldsCompose() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val nameState = validateFieldsStudentUseCase.validateName(_uiState.value.name.valueText)
            val lastNameState = validateFieldsStudentUseCase.validateLastName(_uiState.value.lastName.valueText)
            val secondLastNameState =
                validateFieldsStudentUseCase.validateSecondLastName(_uiState.value.secondLastName.valueText)
            val curpState = validateFieldsStudentUseCase.validateCurp(_uiState.value.curp.valueText)
            val birthdayState = validateFieldsStudentUseCase.validateBirthday(_uiState.value.birthday.valueText)
            val phoneNumberState = validateFieldsStudentUseCase.validatePhoneNumber(_uiState.value.phoneNumber.valueText)

            _uiState.update {
                it.copy(
                    name = nameState,
                    lastName = lastNameState,
                    secondLastName = secondLastNameState,
                    curp = curpState,
                    birthday = birthdayState,
                    phoneNumber = phoneNumberState
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
                    myValue.name.valueText,
                    myValue.lastName.valueText,
                    myValue.secondLastName.valueText,
                    myValue.curp.valueText,
                    myValue.birthday.valueText,
                    myValue.phoneNumber.valueText
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
                name = student.name.stringToModelStateOutFieldText(),
                lastName = student.lastName.stringToModelStateOutFieldText(),
                secondLastName = student.secondLastName.stringToModelStateOutFieldText(),
                curp = student.curp.stringToModelStateOutFieldText(),
                birthday = student.birthday.stringToModelStateOutFieldText(),
                phoneNumber = student.phoneNumber.stringToModelStateOutFieldText(),
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
                        name = studentData[ModelVoiceConstants.NAME].stringToModelStateOutFieldText(),
                        lastName = studentData[ModelVoiceConstants.LAST_NAME].stringToModelStateOutFieldText(),
                        secondLastName = studentData[ModelVoiceConstants.SECOND_LAST_NAME].stringToModelStateOutFieldText(),
                        curp = studentData[ModelVoiceConstants.CURP].stringToModelStateOutFieldText(),
                        birthday = studentData[ModelVoiceConstants.BIRTHDAY].stringToModelStateOutFieldText(),
                        phoneNumber = studentData[ModelVoiceConstants.PHONE_NUMBER].stringToModelStateOutFieldText()
                    )
                }
            }
        }
    }
}