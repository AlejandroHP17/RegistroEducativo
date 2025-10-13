package com.mx.liftechnology.domain.model.student

import android.os.Parcel
import android.os.Parcelable
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetStudent

/**
 * Data model representing a student in the domain layer.
 * This class is Parcelable to allow it to be passed between Android components.
 *
 * @property studentId The student's ID.
 * @property curp The student's CURP.
 * @property birthday The student's birthday.
 * @property phoneNumber The student's phone number.
 * @property userId The student's user ID.
 * @property name The student's name.
 * @property lastName The student's last name.
 * @property secondLastName The student's second last name.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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

/**
 * Extension function to convert a list of [ResponseGetStudent] to a list of [ModelStudentDomain].
 *
 * @receiver A nullable list of [ResponseGetStudent] objects.
 * @return A list of [ModelStudentDomain] objects.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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