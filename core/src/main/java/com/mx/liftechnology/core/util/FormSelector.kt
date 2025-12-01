/**
 * @file Define un enum para identificar los diferentes campos de un formulario.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Enum que representa los diferentes campos de un formulario.
 * Es `Parcelable` para poder pasarlo entre componentes de Android, como argumentos de navegación.
 *
 * @property value El valor entero asociado a cada campo del formulario.
 * @author Pelkidev
 * @version 1.0.0
 */
@Parcelize
enum class FormSelector(val value: Int) : Parcelable {
    /** Representa el campo de nombre. */
    NAME(1),

    /** Representa el campo de apellido paterno. */
    LASTNAME(2),

    /** Representa el campo de apellido materno. */
    SECONDLASTNAME(3),

    /** Representa el campo de CURP. */
    CURP(4),

    /** Representa el campo de número de teléfono. */
    PHONE(5),

    /** Representa el campo de fecha de nacimiento. */
    BIRTHDAY(6),
}
