package com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.VoiceRecognitionManager
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelVoiceConstants
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.RegisterOneStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ValidateFieldsStudentUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterStudentUIState
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorError
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess
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
            logs(results.toString())
            validateDataRecord(results)
        }
    }

    private var isListening = true

    fun onChangeName(name: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                name = name.stringToModelStateOutFieldText()
            ) }
        }
    }
    fun onChangeLastName(lastName: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                lastName = lastName.stringToModelStateOutFieldText()
            ) }
        }
    }
    fun onChangeSecondLastName(secondLastName: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                secondLastName = secondLastName.stringToModelStateOutFieldText()
            ) }
        }
    }
    fun onChangeCurp(curp: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                curp = curp.stringToModelStateOutFieldText()
            ) }
        }
    }
    fun onChangeBirthday(birthday: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                birthday = birthday.stringToModelStateOutFieldText()
            ) }
        }
    }
    fun onChangePhoneNUmber(phoneNumber: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                phoneNumber = phoneNumber.stringToModelStateOutFieldText()
            ) }
        }
    }

    fun validateFieldsCompose() {
        viewModelScope.launch (dispatcherProvider.io){
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

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
                _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
            }
        }
    }

    private suspend fun registerOneStudentCompose() {
        when (val result =
            registerOneStudentUseCase.invoke(
                myValue.name.valueText,
                myValue.lastName.valueText,
                myValue.secondLastName.valueText,
                myValue.curp.valueText,
                myValue.birthday.valueText,
                myValue.phoneNumber.valueText
            )){
            is SuccessState -> {
                _uiState.update { it.copy(
                    uiState = ModelStateUIEnum.SUCCESS,
                    controlToast = ModelStateToastUI(
                        messageToast = R.string.toast_success_register_student,
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
                            messageToast = R.string.toast_error_register_student,
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

    fun getArguments(student: ModelStudentDomain) {
        viewModelScope.launch (dispatcherProvider.main){
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
                _uiState.update { it.copy(buttonColor = colorError) }
            } else {
                voiceRecognitionManager.stopListening()
                isListening = true
                _uiState.update { it.copy(buttonColor = colorSuccess) }
            }
        }
    }

    private fun validateDataRecord(data: List<String>) {
        viewModelScope.launch (dispatcherProvider.main){
            val result = validateVoiceStudentUseCase.buildModelStudent(data.firstOrNull())
            result?.let { studentData ->
                logs(studentData.toString())
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