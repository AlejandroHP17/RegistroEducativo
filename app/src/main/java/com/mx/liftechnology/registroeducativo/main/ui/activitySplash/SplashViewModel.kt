package com.mx.liftechnology.registroeducativo.main.ui.activitySplash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.launch

class SplashViewModel (
    private val dispatcherProvider: DispatcherProvider,
    private val preferenceUseCase: PreferenceUseCase
): ViewModel() {

    // Observer the email field
    private val _navigate = SingleLiveEvent<Boolean>()
    val navigate: LiveData<Boolean> get() = _navigate

    fun getNavigation(){
        viewModelScope.launch(dispatcherProvider.io)  {
            val isLoggedIn = preferenceUseCase.getPreferenceBoolean(ModelPreference.LOGIN)
            _navigate.postValue(isLoggedIn)
        }
    }
}