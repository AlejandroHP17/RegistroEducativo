package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.usecase.flowdata.student.ValidateFieldsStudentUseCase
import com.mx.liftechnology.domain.usecase.flowdata.student.UpdateStudentUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.launch

class EditStudentViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsStudentUseCase: ValidateFieldsStudentUseCase,
    private val updateStudentUseCase: UpdateStudentUseCase

    ) : ViewModel() {

    // Campos observables
    private val _nameField = MutableLiveData<ModelState<Int, Int>>()
    val nameField: LiveData<ModelState<Int, Int>> get() = _nameField

    private val _lastNameField = MutableLiveData<ModelState<Int, Int>>()
    val lastNameField: LiveData<ModelState<Int, Int>> get() = _lastNameField

    private val _secondLastNameField = MutableLiveData<ModelState<Int, Int>>()
    val secondLastNameField: LiveData<ModelState<Int, Int>> get() = _secondLastNameField

    private val _curpField = MutableLiveData<ModelState<Int, Int>>()
    val curpField: LiveData<ModelState<Int, Int>> get() = _curpField

    private val _birthdayField = MutableLiveData<ModelState<Int, Int>>()
    val birthdayField: LiveData<ModelState<Int, Int>> get() = _birthdayField

    private val _phoneNumberField = MutableLiveData<ModelState<Int, Int>>()
    val phoneNumberField: LiveData<ModelState<Int, Int>> get() = _phoneNumberField

    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // Observer the animate loader
    private val _fillFields = SingleLiveEvent<MutableMap<String, String>?>()
    val fillFields: LiveData<MutableMap<String, String>?> get() = _fillFields

    // Observer the response of service
    private val _responseEditStudent = SingleLiveEvent<ModelState<List<String?>?, String>>()
    val responseEditStudent: LiveData<ModelState<List<String?>?, String>> get() = _responseEditStudent

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
                editStudent(name, lastName, secondLastName, curp, birthday, phoneNumber)
            }
        }
    }

    private fun editStudent(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String
    ) {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                updateStudentUseCase.editStudent(
                    name,
                    lastName,
                    secondLastName,
                    curp,
                    birthday,
                    phoneNumber
                )
            }.onSuccess {
                _responseEditStudent.postValue(it)
                _animateLoader.postValue(LoaderState(false))
            }.onFailure {
                _responseEditStudent.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
                _animateLoader.postValue(LoaderState(false))
            }
        }
    }
}