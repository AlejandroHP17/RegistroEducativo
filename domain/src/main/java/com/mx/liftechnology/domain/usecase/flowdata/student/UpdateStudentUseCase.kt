package com.mx.liftechnology.domain.usecase.flowdata.student

import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.registerFlow.CrudStudentRepository

fun interface UpdateStudentUseCase{
    suspend fun editStudent(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String
    ): ModelState<List<String?>?, String>?
}

class UpdateStudentUseCaseImp (
    private val crudStudentRepository: CrudStudentRepository,
    private val preference: PreferenceUseCase
):UpdateStudentUseCase {
    override suspend fun editStudent(
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