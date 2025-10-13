package com.mx.liftechnology.registroeducativo.main.ui.flowMain.profile

import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.preference.PreferenceUseCase

/**
 * ViewModel for the Profile screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ProfileViewModel(
    private val preference: PreferenceUseCase
) : ViewModel() {

    /**
     * Closes the user's session by clearing all preferences.
     */
    fun closeSession() =
        preference.cleanPreference()

}
