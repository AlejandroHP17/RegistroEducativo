package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.subject

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent

class ListSubjectViewModel : ViewModel(){

    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean,Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    fun getSubject(){}

    fun hideLoader(){
        _animateLoader.postValue(LoaderState(false))
    }
}