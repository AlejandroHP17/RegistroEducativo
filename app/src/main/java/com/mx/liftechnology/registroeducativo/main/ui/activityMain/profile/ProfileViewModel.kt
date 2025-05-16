package com.mx.liftechnology.registroeducativo.main.ui.activityMain.profile

import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.preference.PreferenceUseCase

class ProfileViewModel(
    private val preference: PreferenceUseCase
) : ViewModel() {

    fun closeSession() =
        preference.cleanPreference()

}

