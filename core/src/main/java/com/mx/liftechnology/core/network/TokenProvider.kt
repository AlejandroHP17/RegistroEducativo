package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase

class TokenProvider(private val preference: PreferenceUseCase) {
    fun getToken(): String? {
        return preference.getPreferenceString(ModelPreference.ACCESS_TOKEN)
    }
}