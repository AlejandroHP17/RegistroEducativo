package com.mx.liftechnology.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.mx.liftechnology.core.network.callapi.ResponseGetStudent

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

// Función para convertir una lista de ResponseGetStudent a ModelStudent
fun List<ResponseGetStudent?>?.toModelStudentList(): List<ModelStudent> {
    return this?.map { response ->
        ModelStudent(
            studentId = response?.studentId,
            curp = response?.curp,
            birthday = response?.birthday,
            phoneNumber = response?.phoneNumber,
            userId = response?.userId,
            name = response?.name,
            lastName = response?.lastName,
            secondLastName = response?.secondLastName
        )
    }?: emptyList()
}