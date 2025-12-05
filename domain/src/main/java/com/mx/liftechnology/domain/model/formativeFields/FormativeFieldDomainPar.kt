/**
 * @file Define el modelo de dominio para una materia y su mapeo desde la capa de datos.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.formativeFields

import android.os.Parcelable
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
data class FormativeFieldDomainPar(
    val position:Int,
    var name: String?,
    var percent: String?,
    val formativeFieldId: Int?
): Parcelable

/**
 * Función de extensión para convertir un [FormativeFieldDomainPar] (modelo de datos) a un [FormativeFieldDomainPar] (modelo de dominio).
 *
 * @receiver Un objeto [FormativeFieldDomainPar] nulable.
 * @param position La posición del campo formativo en una lista.
 * @return Un objeto [FormativeFieldDomainPar] equivalente, o null si el receiver es null.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun FormativeFieldDomain?.toFormativeFieldDomain(position: Int = 0): FormativeFieldDomainPar? {
    return this?.let {
        FormativeFieldDomainPar(
            position = position,
            name = it.name,
            percent = null,
            formativeFieldId = it.formativeFieldID
        )
    }
}

/**
 * Función de extensión para convertir una lista de [FormativeFieldDomainPar] (modelo de datos)
 * a una lista de [FormativeFieldDomainPar] (modelo de dominio).
 *
 * @receiver Una lista nulable de objetos [FormativeFieldDomainPar].
 * @return Una lista de [FormativeFieldDomainPar], donde los elementos nulos de la entrada han sido omitidos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun List<FormativeFieldDomain?>?.toFormativeFieldDomainList() :List<FormativeFieldDomainPar>{
    return this?.mapIndexedNotNull { index, response ->
        response?.toFormativeFieldDomain(index)
    }?: emptyList()
}
