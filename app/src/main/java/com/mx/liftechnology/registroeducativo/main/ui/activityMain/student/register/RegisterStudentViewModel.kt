package com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.mainflowdomain.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.RegisterOneStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.ValidateFieldsStudentUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.funextensions.log
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterStudentUIState
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

    ) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterStudentUIState())
    val uiState: StateFlow<ModelRegisterStudentUIState> = _uiState.asStateFlow()
    private val myValue: ModelRegisterStudentUIState
        get() = _uiState.value

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
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val nameState = validateFieldsStudentUseCase.validateNameCompose(_uiState.value.name)
            val lastNameState = validateFieldsStudentUseCase.validateLastNameCompose(_uiState.value.lastName)
            val secondLastNameState =
                validateFieldsStudentUseCase.validateSecondLastNameCompose(_uiState.value.secondLastName)
            val curpState = validateFieldsStudentUseCase.validateCurpCompose(_uiState.value.curp)
            val birthdayState = validateFieldsStudentUseCase.validateBirthdayCompose(_uiState.value.birthday)
            val phoneNumberState = validateFieldsStudentUseCase.validatePhoneNumberCompose(_uiState.value.phoneNumber)

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

            if (!(nameState.isError && lastNameState.isError && secondLastNameState.isError && curpState.isError
                && birthdayState.isError && phoneNumberState.isError
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



    // Campos observables
    private val _nameField = MutableLiveData<ModelState<String, String>>()
    val nameField: LiveData<ModelState<String, String>> get() = _nameField

    private val _lastNameField = MutableLiveData<ModelState<String, String>>()
    val lastNameField: LiveData<ModelState<String, String>> get() = _lastNameField

    private val _secondLastNameField = MutableLiveData<ModelState<String, String>>()
    val secondLastNameField: LiveData<ModelState<String, String>> get() = _secondLastNameField

    private val _curpField = MutableLiveData<ModelState<String, String>>()
    val curpField: LiveData<ModelState<String, String>> get() = _curpField

    private val _birthdayField = MutableLiveData<ModelState<String, String>>()
    val birthdayField: LiveData<ModelState<String, String>> get() = _birthdayField

    private val _phoneNumberField = MutableLiveData<ModelState<String, String>>()
    val phoneNumberField: LiveData<ModelState<String, String>> get() = _phoneNumberField

    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // Observer the animate loader
    private val _fillFields = SingleLiveEvent<MutableMap<String, String>?>()
    val fillFields: LiveData<MutableMap<String, String>?> get() = _fillFields

    // Observer the response of service
    private val _responseRegisterStudent = SingleLiveEvent<ModelState<List<String?>?, String>>()
    val responseRegisterStudent: LiveData<ModelState<List<String?>?, String>> get() = _responseRegisterStudent

    fun validateFields(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String
    ) {
        viewModelScope.launch {
            val nameState = validateFieldsStudentUseCase.validateName(name)
            val lastNameState = validateFieldsStudentUseCase.validateLastName(lastName)
            val secondLastNameState =
                validateFieldsStudentUseCase.validateSecondLastName(secondLastName)
            val curpState = validateFieldsStudentUseCase.validateCurp(curp)
            val birthdayState = validateFieldsStudentUseCase.validateBirthday(birthday)
            val phoneNumberState = validateFieldsStudentUseCase.validatePhoneNumber(phoneNumber)

            _nameField.postValue(nameState)
            _lastNameField.postValue(lastNameState)
            _secondLastNameField.postValue(secondLastNameState)
            _curpField.postValue(curpState)
            _birthdayField.postValue(birthdayState)
            _phoneNumberField.postValue(phoneNumberState)

            if (nameState is SuccessState && lastNameState is SuccessState && secondLastNameState is SuccessState && curpState is SuccessState
                && birthdayState is SuccessState && phoneNumberState is SuccessState
            ) {
                _animateLoader.postValue(LoaderState(true))
                registerOneStudent(name, lastName, secondLastName, curp, birthday, phoneNumber)
            }
        }
    }

    private fun registerOneStudent(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String
    ) {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                registerOneStudentUseCase.registerOneStudent(
                    name,
                    lastName,
                    secondLastName,
                    curp,
                    birthday,
                    phoneNumber
                )
            }.onSuccess {
                _responseRegisterStudent.postValue(it)
                _animateLoader.postValue(LoaderState(false))
            }.onFailure {
                _responseRegisterStudent.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
                _animateLoader.postValue(LoaderState(false))
            }
        }
    }

    fun validateDataRecord(data: List<String>) {
        viewModelScope.launch(dispatcherProvider.io) {
            log(data.toString())
            val result = validateVoiceStudentUseCase.buildModelStudent(data.firstOrNull())
            result?.let {
                log(it.toString())
                _fillFields.postValue(it)
            }
        }
    }
}