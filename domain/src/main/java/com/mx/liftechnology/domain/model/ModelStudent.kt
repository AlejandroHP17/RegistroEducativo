package com.mx.liftechnology.domain.model

import android.os.Parcel
import android.os.Parcelable

data class ModelStudent (
    val studentId : String?,
    val curp : String?,
    val birthday : String?,
    val phoneNumber : String?,
    val userId : String?,
    val name : String?,
    val lastName : String?,
    val secondLastName : String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(studentId)
        parcel.writeString(curp)
        parcel.writeString(birthday)
        parcel.writeString(phoneNumber)
        parcel.writeString(userId)
        parcel.writeString(name)
        parcel.writeString(lastName)
        parcel.writeString(secondLastName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelStudent> {
        override fun createFromParcel(parcel: Parcel): ModelStudent {
            return ModelStudent(parcel)
        }

        override fun newArray(size: Int): Array<ModelStudent?> {
            return arrayOfNulls(size)
        }
    }
}