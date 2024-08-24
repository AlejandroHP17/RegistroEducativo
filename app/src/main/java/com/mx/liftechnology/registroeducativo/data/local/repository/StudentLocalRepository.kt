package com.mx.liftechnology.registroeducativo.data.local.repository

import com.mx.liftechnology.registroeducativo.data.local.dao.StudentDao
import com.mx.liftechnology.registroeducativo.data.local.entity.StudentEntity

/** StudentLocalRepository - Get the student in the database
 * @author pelkidev
 * @since 1.0.0
 * @param studentDao Access to Dao with DI
 * @return students
 * */
class StudentLocalRepository(
    private val studentDao: StudentDao
) {
    fun getAllStudent(): List<StudentEntity> = studentDao.getAllStudent()

    fun insertStudent(student: StudentEntity) = studentDao.insertStudent(student)
}