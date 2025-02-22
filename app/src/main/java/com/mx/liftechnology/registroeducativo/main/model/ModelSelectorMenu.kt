package com.mx.liftechnology.registroeducativo.main.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/** Model - Include all the elements to control
 * @author pelkidev
 * @since 1.0.0
 */
@Parcelize
enum class ModelSelectorMenu(val value: String) : Parcelable {
    CONTROL("Control"),
    PROFILE("Perfil"),

    SCHOOL("Escuela"),
    STUDENTS("Alumnos"),
    SUBJECTS("Campo Formativo"),
    PARTIALS("Parciales"),

    ESTUDENTS("Alumnos"),
    CALENDAR("Calendario"),
    EXPORT("Exportar"),
    ESUBJECTS("Campo Formativo")
}
