package com.mx.liftechnology.registroeducativo.main.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/** Model - Include all the elements to control
 * @author pelkidev
 * @since 1.0.0
 */
@Parcelize
enum class ModelSelectorMenu(val value: Int) : Parcelable {
    EVALUATION(1),
    CONTROL(2),
    PROFILE(3),
    CONFIGURATION(4)
}
