package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.subject

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.domain.usecase.flowdata.subject.ReadSubjectUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import kotlinx.coroutines.launch

class ListSubjectViewModel(
    private val readSubjectUseCase: ReadSubjectUseCase
) : ViewModel() {

    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // Observer the animate loader
    private val _responseListSubject = SingleLiveEvent<ModelState<List<String?>?, String>?>()
    val responseListSubject: LiveData<ModelState<List<String?>?, String>?> get() = _responseListSubject

    fun getSubject() {
        viewModelScope.launch {
            runCatching {
                _animateLoader.postValue(LoaderState(true))
                readSubjectUseCase.getListSubject()
            }.onSuccess {
                _responseListSubject.postValue(it)
                _animateLoader.postValue(LoaderState(false))
            }.onFailure {
                _responseListSubject.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
                _animateLoader.postValue(LoaderState(false))
            }
        }
    }

    fun getLocalListSubject() {
        responseListSubject.value?.let {
            _animateLoader.postValue(LoaderState(false))
            _responseListSubject.postValue(_responseListSubject.value)
        }
    }
}