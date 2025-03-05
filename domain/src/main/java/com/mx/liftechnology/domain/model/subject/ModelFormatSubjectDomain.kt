package com.mx.liftechnology.domain.model.subject

import android.os.Parcel
import android.os.Parcelable
import com.mx.liftechnology.core.network.callapi.ResponseGetListSubject

/** Model - to select the dates
 * @author pelkidev
 * @since 1.0.0
 */
data class ModelFormatSubjectDomain(
    val position:Int,
    var name: String?,
    var percent: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(position)
        parcel.writeString(name)
        parcel.writeString(percent)
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


fun List<ResponseGetListSubject?>?.toModelSubjectList() :List<ModelFormatSubjectDomain>{
    return this?.mapIndexed { index, response ->
        ModelFormatSubjectDomain(
            position = index,
            name = response?.subjectDescription,
            percent = null
        )
    }?: emptyList()
}
