package com.mx.liftechnology.domain.model.subject

import android.os.Parcel
import android.os.Parcelable
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListSubject

/**
 * Data model representing the format of a subject in the domain layer.
 * This class is Parcelable to allow it to be passed between Android components.
 *
 * @property position The position of the subject in a list.
 * @property name The name of the subject.
 * @property percent The percentage value associated with the subject.
 * @property subjectId The ID of the subject.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelFormatSubjectDomain(
    val position:Int,
    var name: String?,
    var percent: String?,
    val subjectId: Int?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(position)
        parcel.writeString(name)
        parcel.writeString(percent)
        parcel.writeValue(subjectId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelFormatSubjectDomain> {
        override fun createFromParcel(parcel: Parcel): ModelFormatSubjectDomain {
            return ModelFormatSubjectDomain(parcel)
        }

        override fun newArray(size: Int): Array<ModelFormatSubjectDomain?> {
            return arrayOfNulls(size)
        }
    }
}

/**
 * Extension function to convert a list of [ResponseGetListSubject] to a list of [ModelFormatSubjectDomain].
 *
 * @receiver A nullable list of [ResponseGetListSubject] objects.
 * @return A list of [ModelFormatSubjectDomain] objects.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun List<ResponseGetListSubject?>?.toModelSubjectList() :List<ModelFormatSubjectDomain>{
    return this?.mapIndexed { index, response ->
        ModelFormatSubjectDomain(
            position = index,
            name = response?.subjectDescription,
            percent = null,
            subjectId = response?.subjectTeacherSchoolCycleGroupId
        )
    }?: emptyList()
}
