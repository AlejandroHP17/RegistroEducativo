package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestEditStudent
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.ModelStudentData
import com.mx.liftechnology.data.repository.flowMain.student.EditStudentRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult

class EditStudentUseCase (
    private val editStudentRepository: EditStudentRepository,
    private val preference: PreferenceUseCase
){
    /**
     * Ejecuta el proceso de registro de un estudiante.
     *
     * @param name El nombre del estudiante.
     * @param lastName El apellido paterno del estudiante.
     * @param secondLastName El apellido materno del estudiante.
     * @param curp La CURP del estudiante.
     * @param birthday La fecha de nacimiento del estudiante.
     * @param phoneNumber El número de teléfono del estudiante.
     * @return Un [ModelResult] que indica el resultado de la operación de registro.
     */
    suspend operator fun invoke(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String,
        studentId: Int?
    ): ModelResult<ModelStudentData?, Error> {
        val teacherId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val cycleSchoolId = preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        if(teacherId == null || cycleSchoolId == null || studentId == null ) return ErrorResult(
            LocalError.USER_INCOMPLETE_DATA
        )

        val request = RequestEditStudent(
            name = name,
            lastName = lastName,
            secondLastName = secondLastName,
            curp = curp,
            birthday = birthday,
            phoneNumber = phoneNumber,
            teacherId = teacherId,
            cycleSchoolId = cycleSchoolId,
            isActive = true
        )

        return runCatching { editStudentRepository.executeEditStudent(request, studentId) }.fold(
            onSuccess = { result ->
                when(result){
                    is SuccessResult -> {
                        SuccessResult(result.data)
                    }

                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkError.UNKNOWN)}
        )
    }
}