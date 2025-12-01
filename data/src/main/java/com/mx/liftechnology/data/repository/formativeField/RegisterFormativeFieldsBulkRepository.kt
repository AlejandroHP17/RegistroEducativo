/**
 * @file Define el repositorio para la funcionalidad de registro de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.core.network.api.RequestEvaluations
import com.mx.liftechnology.core.network.api.RequestRegisterFormativeField
import com.mx.liftechnology.core.network.api.RequestWorkType
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.mapperToModelListFormativeFields
import com.mx.liftechnology.data.model.formativeField.FormativeFieldData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el registro de materias.
 * Define el contrato para ejecutar la lógica de registro de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterFormativeFieldsBulkRepository{
    /**
     * Ejecuta la petición de registro de una materia.
     *
     * @param request Los datos de la petición de registro.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeRegisterFormativeFieldsBulk(
        cycleSchoolId : Int,
        formativeFieldName : String,
        code : String,
        workTypes : List<RequestWorkType>,
        evaluations :  List<RequestEvaluations>
    )
    : ModelResult<FormativeFieldData, NetworkModelError>
}

/**
 * Implementación de [RegisterFormativeFieldsBulkRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerFormativeFieldsBulkApiCall La llamada a la API para el registro de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterFormativeFieldsBulkRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
) : RegisterFormativeFieldsBulkRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterFormativeFieldsBulk(
        cycleSchoolId : Int,
        formativeFieldName : String,
        code : String,
        workTypes : List<RequestWorkType>,
        evaluations :  List<RequestEvaluations>
    ): ModelResult<FormativeFieldData, NetworkModelError> {
        val request = RequestRegisterFormativeField(
            cycleSchoolId = cycleSchoolId,
            formativeFieldName = formativeFieldName,
            code = code,
            workTypes = workTypes,
            evaluations = evaluations,
        )

        return try {
            val response = formativeFieldApi.registerFormativeFieldsBulk(request)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToModelListFormativeFields())
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e:Exception){
            ErrorResult(NetworkException.handleException(e))
        }
    }
}