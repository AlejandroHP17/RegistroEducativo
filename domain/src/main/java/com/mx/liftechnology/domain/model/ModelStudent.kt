package com.mx.liftechnology.domain.model

import android.os.Parcel
import android.os.Parcelable

data class ModelStudent (
    val alumno_id : String?,
    val curp : String?,
    val fechanacimiento : String?,
    val celular : String?,
    val user_id : String?,
    val name : String?,
    val paterno : String?,
    val materno : String?
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
        parcel.writeString(alumno_id)
        parcel.writeString(curp)
        parcel.writeString(fechanacimiento)
        parcel.writeString(celular)
        parcel.writeString(user_id)
        parcel.writeString(name)
        parcel.writeString(paterno)
        parcel.writeString(materno)
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