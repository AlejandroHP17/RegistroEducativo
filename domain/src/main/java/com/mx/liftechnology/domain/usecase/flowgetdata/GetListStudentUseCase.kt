package com.mx.liftechnology.domain.usecase.flowgetdata

import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorStateUser
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.network.callapi.CredentialGetListStudent
import com.mx.liftechnology.core.network.callapi.ResponseGetStudent
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultSuccess
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.getFlow.GetListStudentRepository
import com.mx.liftechnology.domain.model.ModelStudent

fun interface GetListStudentUseCase {
    suspend fun getListStudent(): ModelState<List<ModelStudent?>?, String>?
}

class GetListStudentUseCaseImp (
    private val getListStudentRepository : GetListStudentRepository,
    private val preference: PreferenceUseCase
) : GetListStudentUseCase{
    override suspend fun getListStudent(): ModelState<List<ModelStudent?>?, String>? {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val pecg= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = CredentialGetListStudent(
            profesor_id = roleId,
            user_id = userId,
            profesorescuelaciclogrupo_id = pecg
        )

        return when (val result =  getListStudentRepository.executeGetListStudent(request)) {
            is ResultSuccess -> {

                SuccessState(result.data?.toModelStudentList())
            }
            is ResultError -> {
                handleResponse(result.error)
            }
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * if not return the correct error
     * @return ModelState
     */
    private fun handleResponse(error: FailureService): ModelState<List<ModelStudent?>?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorStateUser(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorStateUser(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    // Función para convertir una lista de ResponseGetStudent a ModelStudent
    private fun List<ResponseGetStudent?>.toModelStudentList(): List<ModelStudent> {
        return this.map { response ->
            ModelStudent(
                alumno_id = response?.alumno_id,
                curp = response?.curp,
                fechanacimiento = response?.fechanacimiento,
                celular = response?.celular,
                user_id = response?.user_id,
                name = response?.name,
                paterno = response?.paterno,
                materno = response?.materno
            )
        }
    }


}