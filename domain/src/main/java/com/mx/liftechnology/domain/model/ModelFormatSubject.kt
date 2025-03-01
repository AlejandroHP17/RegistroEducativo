package com.mx.liftechnology.domain.model

import com.mx.liftechnology.core.network.callapi.ResponseGetListSubject

/** Model - to select the dates
 * @author pelkidev
 * @since 1.0.0
 */
data class ModelFormatSubject(
    val position:Int,
    var name: String?,
    var percent: String?
)


fun List<ResponseGetListSubject?>?.toModelSubjectList() :List<ModelFormatSubject>{
    return this?.mapIndexed { index, response ->
        ModelFormatSubject(
            position = index,
            name = response?.subjectDescription,
            percent = null
        )
    }?: emptyList()
}
