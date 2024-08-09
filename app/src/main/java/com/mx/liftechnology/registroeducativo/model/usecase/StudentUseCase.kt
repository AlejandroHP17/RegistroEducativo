package com.mx.liftechnology.registroeducativo.model.usecase

import com.mx.liftechnology.registroeducativo.data.local.entity.StudentEntity
import com.mx.liftechnology.registroeducativo.data.local.repository.StudentLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudentUseCase( private val localStudentRepository: StudentLocalRepository) {

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
    /** Obtiene el listado de categorias y procesa la informacion para el viewmodel
     * @author pelkidev
     * @date 28/08/2023
     * */
    suspend fun insertStudent(studentData: StudentEntity) {
        return withContext(dispatcher) {
            localStudentRepository.insertStudent(studentData)
        }
    }
}