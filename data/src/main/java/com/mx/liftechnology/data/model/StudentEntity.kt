package com.mx.liftechnology.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/** StudentEntity - Model of Student for the databse
 * @author pelkidev
 * @since 1.0.0
 * */

@Parcelize
@Entity(tableName = "student_table")

data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "idCurp") val idCurp: String?,
    val name: String?,
    val lastName: String?,
    val secondLastName: String?,
    val dateBirthday: String?,
    val age: Int?,
    val listNumber: Int?,
    val phoneNumber: Long?,
) : Parcelable
