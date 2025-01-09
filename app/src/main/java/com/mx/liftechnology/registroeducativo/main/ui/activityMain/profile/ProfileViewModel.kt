package com.mx.liftechnology.registroeducativo.main.ui.activityMain.profile

import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase

class ProfileViewModel(
    private val preference: PreferenceUseCase
): ViewModel() {

    fun closeSession() : Boolean{
        preference.savePreferenceBoolean(ModelPreference.LOGIN, false)
        preference.savePreferenceString(ModelPreference.ACCESS_TOKEN, null)
        preference.savePreferenceInt(ModelPreference.ID_USER, null)
        preference.savePreferenceInt(ModelPreference.ID_ROLE, null)
        preference.savePreferenceString(ModelPreference.USER_ROLE,null)
        return true
    }
}

