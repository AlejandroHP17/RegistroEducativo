package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.subject

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.model.ModelFormatSubject
import com.mx.liftechnology.domain.usecase.flowregisterdata.RegisterSubjectUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.ValidateFieldsSubjectUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.launch

class RegisterSubjectViewModel (
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsSubjectUseCase : ValidateFieldsSubjectUseCase,
    private val registerSubjectUseCase : RegisterSubjectUseCase
): ViewModel(){

    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean,Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // Observer the period select by user
    private val _subjectNumber = SingleLiveEvent<Int>()
    val subjectNumber: LiveData<Int> get() = _subjectNumber

    // Observer the period select by user
    private val _nameField = SingleLiveEvent<ModelState<Int, Int>>()
    val nameField: LiveData<ModelState<Int, Int>> get() = _nameField

    // Observer the date selected by user
    private val _adapterField = SingleLiveEvent<ModelState<Int, String>>()
    val adapterField: LiveData<ModelState<Int, String>> get() = _adapterField


    fun saveSubject(data:String?){
        val subjectNumber = data?.toIntOrNull() ?: 0
        _subjectNumber.postValue(subjectNumber)
    }

    fun hideLoader(){
        _animateLoader.postValue(LoaderState(false))
    }

    fun validateFields(updatedList: MutableList<ModelFormatSubject>?, name: String?) {
        viewModelScope.launch (dispatcherProvider.io) {
            val nameState = validateFieldsSubjectUseCase.validateName(name)
            val updatedListState = validateFieldsSubjectUseCase.validateListJobs(updatedList)

            _nameField.postValue(nameState)
            _adapterField.postValue(updatedListState)

            if (nameState is SuccessState && updatedListState is SuccessState) {
                registerSubject(updatedList,name)
            }
        }
    }

    private fun registerSubject(updatedList: MutableList<ModelFormatSubject>?, name: String?){
        viewModelScope.launch (dispatcherProvider.io){
            runCatching {
                registerSubjectUseCase.putSubjects(updatedList,name)
            }.onSuccess {

            }.onFailure {

            }
        }
    }
}