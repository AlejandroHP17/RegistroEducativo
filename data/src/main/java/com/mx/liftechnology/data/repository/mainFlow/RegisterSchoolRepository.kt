package com.mx.liftechnology.data.repository.mainFlow

import com.mx.liftechnology.core.model.modelApi.CctSchool
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorStateUser
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.network.callapi.CredentialsRegisterSchool
import com.mx.liftechnology.core.network.callapi.RegisterSchoolApiCall
import retrofit2.Response

fun interface RegisterSchoolRepository{
  suspend fun executeRegisterSchool(
      request: CctSchool?,
      grade: String,
      group: String,
      cycle: String,
      userId: Int?,
      roleId: Int?
  ): ModelState<String?, String>
}

class RegisterSchoolRepositoryImp(
    private val registerApiCall: RegisterSchoolApiCall
) :  RegisterSchoolRepository {

    /** Execute the user register
     * @author pelkidev
     * @since 1.0.0
     * @param email the user
     * @param pass the user
     * @param activationCode the user
     */
    override suspend fun executeRegisterSchool(
        request: CctSchool?,
        grade: String,
        group: String,
        cycle: String,
        userId: Int?,
        roleId: Int?
    ): ModelState<String?, String> {
        return try {
            val request = CredentialsRegisterSchool(
                cct = request?.cct,
                tipocicloescolar_id = request?.tipocicloescolar_id,
                grado = grade,
                nombregrupo = group,
                anio = "2024",
                periodo = cycle,
                profesor_id = roleId,
                user_id = userId,
            )
            val response = registerApiCall.callApi(request)
            handleResponse(response)
        } catch (e: Exception) {
            ErrorState(ModelCodeError.ERROR_CATCH )
        }
    }

    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * @param responseBody in order to validate the code and if is success, return the body
     * if not return the correct error
     * @return ModelState
     */
    private fun handleResponse(responseBody: Response<GenericResponse<String>?>): ModelState<String?, String> {
        return when (responseBody.code()) {
            200 -> SuccessState(responseBody.body()?.data)
            400 -> ErrorStateUser(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            401 -> ErrorStateUser(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            500 -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

}