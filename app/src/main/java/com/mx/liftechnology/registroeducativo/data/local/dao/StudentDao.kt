package com.mx.liftechnology.registroeducativo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mx.liftechnology.registroeducativo.data.local.entity.StudentEntity

@Dao
interface StudentDao {

    @Query("SELECT * FROM student_table ORDER BY id ASC")
    fun getAllStudent(): List<StudentEntity>

    @Insert
    fun insertStudent(student:StudentEntity)


}