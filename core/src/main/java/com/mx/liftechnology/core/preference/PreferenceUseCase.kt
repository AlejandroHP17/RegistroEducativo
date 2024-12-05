package com.mx.liftechnology.core.preference

/** PreferenceUseCase - Get the preference
 * @author pelkidev
 * @date 28/08/2023
 * */
class PreferenceUseCase (private val preference: PreferenceRepository){

    fun savePreferenceBoolean(name: String, value: Boolean){
        preference.savePreference(name, value)
    }

    fun getPreferenceBoolean(name: String): Boolean {
        return preference.getPreference(name, false)
    }

     fun savePreferenceString(name: String, value: String?){
        preference.savePreference(name, value)
    }

    fun getPreferenceString(name: String): String? {
        return preference.getPreference(name, null)
    }

     fun savePreferenceInt(name: String, value: Int?){
        preference.savePreference(name, value)
    }

    fun getPreferenceInt(name: String): Int? {
        return preference.getPreference(name, null)
    }
}