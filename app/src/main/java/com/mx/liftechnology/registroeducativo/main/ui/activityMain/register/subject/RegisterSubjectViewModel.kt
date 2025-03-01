package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.subject

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.ModelFormatSubject
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.flowdata.subject.CreateSubjectUseCase
import com.mx.liftechnology.domain.usecase.flowdata.subject.ValidateFieldsSubjectUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.launch

class RegisterSubjectViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsSubjectUseCase: ValidateFieldsSubjectUseCase,
    private val createSubjectUseCase: CreateSubjectUseCase
) : ViewModel() {

    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // Observer the period select by user
    private val _subjectNumber = SingleLiveEvent<Int>()
    val subjectNumber: LiveData<Int> get() = _subjectNumber

    // Observer the period select by user
    private val _nameField = SingleLiveEvent<ModelState<String, String>>()
    val nameField: LiveData<ModelState<String, String>> get() = _nameField

    // Observer the date selected by user
    private val _adapterField = SingleLiveEvent<ModelState<String, String>>()
    val adapterField: LiveData<ModelState<String, String>> get() = _adapterField

    // Observer the animate loader
    private val _responseSubjectRegister = SingleLiveEvent<ModelState<List<String?>?, String>?>()
    val responseSubjectRegister: LiveData<ModelState<List<String?>?, String>?> get() = _responseSubjectRegister


    fun saveSubject(data: String?) {
        val subjectNumber = data?.toIntOrNull() ?: 0
        _subjectNumber.postValue(subjectNumber)
    }

    fun validateFields(updatedList: MutableList<ModelFormatSubject>?, name: String?) {
        viewModelScope.launch(dispatcherProvider.io) {
            _animateLoader.postValue(LoaderState(true))
            val nameState = validateFieldsSubjectUseCase.validateName(name)
            val updatedListState = validateFieldsSubjectUseCase.validateListJobs(updatedList)

            _nameField.postValue(nameState)
            _adapterField.postValue(updatedListState)

            if (nameState is SuccessState && updatedListState is SuccessState) {
                registerSubject(updatedList, name)
            }
        }
    }

    private fun registerSubject(updatedList: MutableList<ModelFormatSubject>?, name: String?) {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                createSubjectUseCase.putSubjects(updatedList, name)
            }.onSuccess {
                _animateLoader.postValue(LoaderState(false))
                _responseSubjectRegister.postValue(it)
            }.onFailure {
                _animateLoader.postValue(LoaderState(false))
                _responseSubjectRegister.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }

    fun loaderState(visible: Boolean){
        _animateLoader.postValue(LoaderState(visible))
    }
}