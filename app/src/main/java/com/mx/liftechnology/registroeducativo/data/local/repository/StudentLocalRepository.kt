package com.mx.liftechnology.registroeducativo.data.local.repository

import com.mx.liftechnology.registroeducativo.data.local.dao.StudentDao
import com.mx.liftechnology.registroeducativo.data.local.entity.StudentEntity

class StudentLocalRepository(
    private val studentDao: StudentDao
) {
    fun getAllStudent(): List<StudentEntity> = studentDao.getAllStudent()
    fun postStudent(student : StudentEntity) = studentDao.insertStudent(student)
}