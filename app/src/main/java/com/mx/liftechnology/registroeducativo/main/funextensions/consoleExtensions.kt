package com.mx.liftechnology.registroeducativo.main.funextensions

import android.util.Log
import com.mx.liftechnology.registroeducativo.BuildConfig

/** Print logs depending the flavor
 * @author pelkidev
 * @since 1.0.0
 */
fun Any.log(tag: String = "DEBUG_LOG") {
    if (BuildConfig.LOG_TAG)
        Log.d(tag, this.toString())
}