package com.mx.liftechnology.registroeducativo.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "student_table")

data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    @ColumnInfo(name = "idCurp")  val idCurp : String?,
    val firstName : String?,
    val secondName : String?,
    val lastName : String?,
    val secondLastName : String?,
    val dateBirthday : Date?,
    val age : Int?,
    val listNumber : Int?,
    val phoneNumber : Int?,
    //val address : ModelAddress?
):Parcelable
