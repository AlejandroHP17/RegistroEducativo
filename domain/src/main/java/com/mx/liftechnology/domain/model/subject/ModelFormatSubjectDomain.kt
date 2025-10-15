/**
 * @file Define el modelo de dominio para una materia y su mapeo desde la capa de datos.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.subject

import android.os.Parcelable
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListSubject
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
data class ModelFormatSubjectDomain(
    val position:Int,
    var name: String?,
    var percent: String?,
    val subjectId: Int?
): Parcelable

/**
 * Función de extensión para convertir una lista de [ResponseGetListSubject] (modelo de red)
 * a una lista de [ModelFormatSubjectDomain] (modelo de dominio).
 *
 * @receiver Una lista nulable de objetos [ResponseGetListSubject].
 * @return Una lista de [ModelFormatSubjectDomain], donde los elementos nulos de la entrada han sido omitidos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun List<ResponseGetListSubject?>?.toModelSubjectList() :List<ModelFormatSubjectDomain>{
    return this?.mapIndexedNotNull { index, response ->
        response?.let {
            ModelFormatSubjectDomain(
                position = index,
                name = it.subjectDescription,
                percent = null,
                subjectId = it.subjectTeacherSchoolCycleGroupId
            )
        }
    }?: emptyList()
}
