package com.mx.liftechnology.domain.model.student

import android.os.Parcel
import android.os.Parcelable
import com.mx.liftechnology.core.network.callapi.ResponseGetStudent

data class ModelStudentDomain (
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

    companion object CREATOR : Parcelable.Creator<ModelStudentDomain> {
        override fun createFromParcel(parcel: Parcel): ModelStudentDomain {
            return ModelStudentDomain(parcel)
        }

        override fun newArray(size: Int): Array<ModelStudentDomain?> {
            return arrayOfNulls(size)
        }
    }
}

// Función para convertir una lista de ResponseGetStudent a ModelStudentDomain
fun List<ResponseGetStudent?>?.toModelStudentList(): List<ModelStudentDomain> {
    return this?.map { response ->
        ModelStudentDomain(
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