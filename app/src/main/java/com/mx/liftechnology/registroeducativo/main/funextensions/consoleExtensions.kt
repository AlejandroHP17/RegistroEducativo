package com.mx.liftechnology.registroeducativo.main.funextensions

import com.mx.liftechnology.registroeducativo.BuildConfig
import timber.log.Timber

/** Print logs depending the flavor
 * @author pelkidev
 * @since 1.0.0
 */

inline fun <reified T> T.log(message: String, name:String = "Desarrollo: ") {
    if (BuildConfig.LOG_TAG){
        val tag = T::class.java.simpleName // Obtiene el nombre de la clase
        Timber.tag(tag).i(name + message)
    }
}