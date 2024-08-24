package com.mx.liftechnology.registroeducativo.model.usecase

import com.mx.liftechnology.registroeducativo.data.local.entity.StudentEntity
import com.mx.liftechnology.registroeducativo.data.local.repository.StudentLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** MenuUseCase - Get the list of menu and process the information
 * @author pelkidev
 * @date 28/08/2023
 * @param localStudentRepository connect with the repository
 * */
class StudentUseCase(private val localStudentRepository: StudentLocalRepository) {

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    /** insertStudent - Insert a student in DAO
     * @author pelkidev
     * @date 28/08/2023
     * */
    suspend fun insertStudent(studentData: StudentEntity) {
        return withContext(dispatcher) {
            localStudentRepository.insertStudent(studentData)
        }
    }

    /** getAllStudents - get all students in DAO
     * @author pelkidev
     * @date 28/08/2023
     * */
    suspend fun getAllStudents(): List<StudentEntity> {
        return withContext(dispatcher) {
            localStudentRepository.getAllStudent()
        }
    }
}