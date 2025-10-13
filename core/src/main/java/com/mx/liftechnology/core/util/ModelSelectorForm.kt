package com.mx.liftechnology.core.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Enum representing the different fields in a form.
 * This is Parcelable to allow it to be passed between Android components.
 *
 * @property value The integer value associated with the form field.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
@Parcelize
enum class ModelSelectorForm(val value: Int) : Parcelable {
    /** Represents the name field. */
    NAME(1),

    /** Represents the last name field. */
    LASTNAME(2),

    /** Represents the second last name field. */
    SECONDLASTNAME(3),

    /** Represents the CURP field. */
    CURP(4),

    /** Represents the phone number field. */
    PHONE(5),

    /** Represents the birthday field. */
    BIRTHDAY(6),
}
