package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.VoiceRecognitionManager
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.ModelVoiceConstants
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.RegisterOneStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ValidateFieldsStudentUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelRegisterStudentUiState
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorError
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel for the Student Registration screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterStudentViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsStudentUseCase: ValidateFieldsStudentUseCase,
    private val registerOneStudentUseCase: RegisterOneStudentUseCase,
    private val validateVoiceStudentUseCase: ValidateVoiceStudentUseCase,
    private val voiceRecognitionManager: VoiceRecognitionManager
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterStudentUiState())
    /** The UI state for the screen. */
    val uiState: StateFlow<ModelRegisterStudentUiState> = _uiState.asStateFlow()
    private val myValue: ModelRegisterStudentUiState
        get() = _uiState.value

    private var isListening = true

    init {
        // Observa los resultados del reconocimiento de voz usando StateFlow
        voiceRecognitionManager.resultsStateFlow
            .onEach { results ->
                logs(results.toString())
                validateDataRecord(results)
            }
            .launchIn(viewModelScope)
    }

    /**
     * Called when the name input changes.
     *
     * @param name The new name value.
     */
    fun onChangeName(name: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                name = name.stringToModelStateOutFieldText()
            ) }
        }
    }

    /**
     * Called when the last name input changes.
     *
     * @param lastName The new last name value.
     */
    fun onChangeLastName(lastName: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                lastName = lastName.stringToModelStateOutFieldText()
            ) }
        }
    }

    /**
     * Called when the second last name input changes.
     *
     * @param secondLastName The new second last name value.
     */
    fun onChangeSecondLastName(secondLastName: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                secondLastName = secondLastName.stringToModelStateOutFieldText()
            ) }
        }
    }

    /**
     * Called when the CURP input changes.
     *
     * @param curp The new CURP value.
     */
    fun onChangeCurp(curp: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            validateCurpWithBirthday(curp)
            _uiState.update { it.copy(
                curp = curp.stringToModelStateOutFieldText()
            ) }
        }
    }

    private fun validateCurpWithBirthday(curp: String) {
        if (curp.length > 10) {
            val rawDate = curp.substring(4, 10)

            return try {
                val year = rawDate.substring(0, 2).toInt()
                val month = rawDate.substring(2, 4).toInt()
                val day = rawDate.substring(4, 6).toInt()

                val fullYear = if (year <= 29) 2000 + year else 1900 + year

                val localDate = LocalDate.of(fullYear, month, day)
                onChangeBirthday( localDate.toString())
            } catch (_: Exception) {
                //Nothing
            }
        }
    }

    /**
     * Called when the birthday input changes.
     *
     * @param birthday The new birthday value.
     */
    fun onChangeBirthday(birthday: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                birthday = birthday.stringToModelStateOutFieldText()
            ) }
        }
    }

    /**
     * Called when the phone number input changes.
     *
     * @param phoneNumber The new phone number value.
     */
    fun onChangePhoneNUmber(phoneNumber: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(
                phoneNumber = phoneNumber.stringToModelStateOutFieldText()
            ) }
        }
    }

    /**
     * Validates the input fields and proceeds to register the student if they are valid.
     */
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
            is SuccessResult -> {
                _uiState.update { it.copy(
                    uiState = ModelStateUIEnum.SUCCESS,
                    controlToast = ModelStateToastUI(
                        messageToast = R.string.toast_success_register_student,
                        showToast = true,
                        typeToast = ModelStateTypeToastUI.SUCCESS
                    )
                ) }
            }
            is ErrorUserResult -> {
                _uiState.update { it.copy(
                    uiState = ModelStateUIEnum.ERROR,
                    controlToast = ModelStateToastUI(
                        messageToast = R.string.toast_error_register_student,
                        showToast = true,
                        typeToast = ModelStateTypeToastUI.ERROR
                    )
                ) }
            }
            else -> {
                logs(result.toString())
                _uiState.update { it.copy(
                    uiState = ModelStateUIEnum.ERROR
                ) }
            }
        }
    }

    /**
     * Gets the arguments for the student.
     *
     * @param student The student data.
     */
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

    /**
     * Called when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        voiceRecognitionManager.release()
    }

    /**
     * Toggles the voice recognition listening state.
     */
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

    /**
     * Modifies the visibility of the toast message.
     *
     * @param show True to show the toast, false to hide it.
     */
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