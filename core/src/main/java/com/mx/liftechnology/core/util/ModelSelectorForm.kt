package com.mx.liftechnology.core.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/** Model - Include all the elements to control
 * @author pelkidev
 * @since 1.0.0
 */
@Parcelize
enum class ModelSelectorForm(val value: Int) : Parcelable {
    NAME(1),
    LASTNAME(2),
    SECONDLASTNAME(3),
    CURP(4),
    PHONE(5),
    BIRTHDAY(6),
}
