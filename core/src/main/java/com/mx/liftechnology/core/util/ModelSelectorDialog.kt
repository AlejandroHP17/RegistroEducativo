package com.mx.liftechnology.core.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/** Model - Include all the elements to control
 * @author pelkidev
 * @since 1.0.0
 */
@Parcelize
enum class ModelSelectorDialog(val value: Int) : Parcelable {
    ADD(0)
}
