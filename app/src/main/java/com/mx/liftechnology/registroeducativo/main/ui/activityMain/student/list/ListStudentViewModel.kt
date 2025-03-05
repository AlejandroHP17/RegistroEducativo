package com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.GetListStudentUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import kotlinx.coroutines.launch

class ListStudentViewModel(
    private val getListStudentUseCase: GetListStudentUseCase
) : ViewModel() {

    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // Observer the animate loader
    private val _responseListStudent = SingleLiveEvent<ModelState<List<ModelStudentDomain>?, String>?>()
    val responseListStudent: LiveData<ModelState<List<ModelStudentDomain>?, String>?> get() = _responseListStudent

    fun getListStudent() {
        viewModelScope.launch {
            runCatching {
                _animateLoader.postValue(LoaderState(true))
                getListStudentUseCase.getListStudent()
            }.onSuccess {
                _responseListStudent.postValue(it)
                _animateLoader.postValue(LoaderState(false))
            }.onFailure {
                _responseListStudent.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
                _animateLoader.postValue(LoaderState(false))
            }
        }
    }

    fun getLocalListStudent() {
        responseListStudent.value?.let {
            _animateLoader.postValue(LoaderState(false))
            _responseListStudent.postValue(_responseListStudent.value)
        }
    }
}