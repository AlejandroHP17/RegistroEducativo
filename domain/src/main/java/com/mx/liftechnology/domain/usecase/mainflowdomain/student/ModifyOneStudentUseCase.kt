package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.student.RegisterStudentRepository
import com.mx.liftechnology.domain.model.generic.ModelState

/**
 * Interface for modifying a single student's information.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface ModifyOneStudentUseCase{
    /**
     * Modifies the information of a single student.
     *
     * @param name The student's name.
     * @param lastName The student's last name.
     * @param secondLastName The student's second last name.
     * @param curp The student's CURP.
     * @param birthday The student's birthday.
     * @param phoneNumber The student's phone number.
     * @return A [ModelState] indicating the result of the modification, or null.
     */
    suspend fun modifyOneStudent(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String
    ): ModelState<List<String?>?, String>?
}

/**
 * Implementation of [ModifyOneStudentUseCase].
 *
 * @property crudStudentRepository The repository for student CRUD operations.
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ModifyOneStudentUseCaseImp (
    private val crudStudentRepository: RegisterStudentRepository,
    private val preference: PreferenceUseCase
):ModifyOneStudentUseCase {
    /**
     * {@inheritDoc}
     */
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