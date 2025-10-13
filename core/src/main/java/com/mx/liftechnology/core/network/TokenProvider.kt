package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase

/**
 * Provides the authentication token from user preferences.
 *
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class TokenProvider(private val preference: PreferenceUseCase) {
    /**
     * Gets the authentication token.
     *
     * @return The token, or null if not found.
     */
    fun getToken(): String? {
        return preference.getPreferenceString(ModelPreference.ACCESS_TOKEN)
    }
}