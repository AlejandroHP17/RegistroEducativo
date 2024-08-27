package com.mx.liftechnology.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mx.liftechnology.data.model.StudentEntity


/** Interface - Connect with the database (Students)
 * @author pelkidev
 * @since 1.0.0
 * */
@Dao
interface StudentDao {

    @Query("SELECT * FROM student_table ORDER BY idCurp ASC")
    fun getAllStudent(): List<StudentEntity>

    @Insert
    fun insertStudent(student: StudentEntity)

}