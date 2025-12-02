/**
 * @file Define el modelo de dominio para una materia y su mapeo desde la capa de datos.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.formativeFields

import android.os.Parcelable
import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.model.formativeField.FormativeFieldData
import kotlinx.parcelize.Parcelize

/**
 * Modelo de datos que representa el formato de una materia en la capa de dominio.
 * Esta clase es `Parcelable` para permitir que se pase entre componentes de Android.
 *
 * @property position La posición de la materia en una lista.
 * @property name El nombre de la materia.
 * @property percent El valor porcentual asociado a la materia.
 * @property formativeFieldId El identificador único de la materia.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
@Parcelize
data class FormativeFieldDomain(
    val position:Int,
    var name: String?,
    var percent: String?,
    val formativeFieldId: Int?
): Parcelable

/**
 * Función de extensión para convertir una lista de [FormativeFieldData] (modelo de datos)
 * a una lista de [FormativeFieldDomain] (modelo de dominio).
 *
 * @receiver Una lista nulable de objetos [FormativeFieldData].
 * @return Una lista de [FormativeFieldDomain], donde los elementos nulos de la entrada han sido omitidos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun List<FormativeFieldData?>?.toFormativeFieldDomainList() :List<FormativeFieldDomain>{
    return this?.mapIndexedNotNull { index, response ->
        response?.let {
            FormativeFieldDomain(
                position = index,
                name = it.name,
                percent = null,
                formativeFieldId = it.formativeFieldID
            )
        }
    }?: emptyList()
}
