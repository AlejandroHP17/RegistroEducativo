package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.mainflowdata.student.CrudStudentRepository
import com.mx.liftechnology.domain.model.generic.ModelState

fun interface ModifyOneStudentUseCase{
    suspend fun modifyOneStudent(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String
    ): ModelState<List<String?>?, String>?
}

class ModifyOneStudentUseCaseImp (
    private val crudStudentRepository: CrudStudentRepository,
    private val preference: PreferenceUseCase
):ModifyOneStudentUseCase {
    override suspend fun modifyOneStudent(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String,
    ): ModelState<List<String?>?, String>? {
        TODO("Not yet implemented")
    }

}