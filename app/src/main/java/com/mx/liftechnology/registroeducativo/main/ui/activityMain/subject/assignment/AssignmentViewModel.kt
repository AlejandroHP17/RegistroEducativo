package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent

class AssignmentViewModel : ViewModel() {
    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    fun loaderState(visible: Boolean){
        _animateLoader.postValue(LoaderState(visible))
    }

}