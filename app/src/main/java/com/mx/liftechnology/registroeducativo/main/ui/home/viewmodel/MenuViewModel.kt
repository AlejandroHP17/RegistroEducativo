package com.mx.liftechnology.registroeducativo.main.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.model.dataclass.ErrorState
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelAdapterMenu
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelCodeError
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelState
import com.mx.liftechnology.registroeducativo.model.usecase.MenuUseCase
import kotlinx.coroutines.launch

class MenuViewModel(
    private val useCase: MenuUseCase
) : ViewModel() {

    // Corrutina controlada
    private val coroutine = CoroutineScopeManager()

    private val _nameCourse = MutableLiveData<String>("")
    val nameCourse: LiveData<String> = _nameCourse

    private val _nameMenu = MutableLiveData<ModelState<List<ModelAdapterMenu>>>()
    val nameMenu: LiveData<ModelState<List<ModelAdapterMenu>>> = _nameMenu

    fun saveNameCourse(value: String){
        _nameCourse.value = value
    }

    fun getMenu(){
        coroutine.scopeIO.launch {
            runCatching {
                useCase.getMenu()
            }.onSuccess {
                _nameMenu.postValue(it)
            }.onFailure {
                _nameMenu.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
            }
        }
    }
}