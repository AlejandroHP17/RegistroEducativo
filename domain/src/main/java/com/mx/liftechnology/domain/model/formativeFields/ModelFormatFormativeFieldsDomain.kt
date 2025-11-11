/**
 * @file Define el modelo de dominio para una materia y su mapeo desde la capa de datos.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.formativeFields

import android.os.Parcelable
import com.mx.liftechnology.core.network.apiCall.formativeField.ResponseGetListFormativeField
import com.mx.liftechnology.data.model.formativeField.ModelFormativeFieldData
import kotlinx.parcelize.Parcelize

/**
 * Modelo de datos que representa el formato de una materia en la capa de dominio.
 * Esta clase es `Parcelable` para permitir que se pase entre componentes de Android.
 *
 * @property position La posición de la materia en una lista.
 * @property name El nombre de la materia.
 * @property percent El valor porcentual asociado a la materia.
 * @property subjectId El identificador único de la materia.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
@Parcelize
data class ModelFormatFormativeFieldsDomain(
    val position:Int,
    var name: String?,
    var percent: String?,
    val subjectId: Int?
): Parcelable

/**
 * Función de extensión para convertir una lista de [ResponseGetListFormativeField] (modelo de red)
 * a una lista de [ModelFormatFormativeFieldsDomain] (modelo de dominio).
 *
 * @receiver Una lista nulable de objetos [ResponseGetListFormativeField].
 * @return Una lista de [ModelFormatFormativeFieldsDomain], donde los elementos nulos de la entrada han sido omitidos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun List<ModelFormativeFieldData?>?.toModelListFormativeFields() :List<ModelFormatFormativeFieldsDomain>{
    return this?.mapIndexedNotNull { index, response ->
        response?.let {
            ModelFormatFormativeFieldsDomain(
                position = index,
                name = it.name,
                percent = null,
                subjectId = it.formativeFieldID
            )
        }
    }?: emptyList()
}
