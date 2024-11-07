package com.mx.liftechnology.domain.usecase

import com.mx.liftechnology.data.repository.PreferenceRepository

/** PreferenceUseCase - Get the preference
 * @author pelkidev
 * @date 28/08/2023
 * */
class PreferenceUseCase (private val preference: PreferenceRepository){

    suspend fun savePreferenceBoolean(name: String, value: Boolean){
        preference.savePreference(name, value)
    }

    suspend fun getPreferenceBoolean(name: String): Boolean {
        return preference.getPreference(name, false) // `false` es el valor predeterminado
    }

}