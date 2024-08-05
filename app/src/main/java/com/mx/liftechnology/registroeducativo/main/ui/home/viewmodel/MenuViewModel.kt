package com.mx.liftechnology.registroeducativo.main.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {

    private val _nameCourse = MutableLiveData<String>("")
    val nameCourse: LiveData<String> = _nameCourse

    fun saveNameCourse(value: String){
        _nameCourse.value = value
    }
}