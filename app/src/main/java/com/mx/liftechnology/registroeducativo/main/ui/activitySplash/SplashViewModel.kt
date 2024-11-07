package com.mx.liftechnology.registroeducativo.main.ui.activitySplash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.data.model.ModelPreference
import com.mx.liftechnology.domain.usecase.PreferenceUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent

class SplashViewModel (
    private val preferenceUseCase: PreferenceUseCase
): ViewModel() {

    // Observer the email field
    private val _navigate = SingleLiveEvent<Boolean>()
    val navigate: LiveData<Boolean> get() = _navigate

    suspend fun getNavigation(){
        val isLoggedIn = preferenceUseCase.getPreferenceBoolean(ModelPreference.LOGIN)
        _navigate.postValue(isLoggedIn)
    }
}