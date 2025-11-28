/**
 * @file Define la llamada a la API para obtener la lista de tipos de evaluación y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.evaluation

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz para la llamada a la API de obtención de la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListWorkTypeStudentApiCall{
    /**
     * Realiza la petición a la API para obtener la lista de tipos de evaluación.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de strings.
     */
    @GET(Environment.END_POINT_GET_WORK_TYPE_STUDENT)
    suspend fun callApi(
        @Query ("formative_field_id") formativeFieldId:Int
    ): Response<ResponseGeneric<ResponseGetListWorkStudents>>
}

/**
 * Modelo de datos para la petición de la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetListWorkStudents(
    @SerializedName("formative_field_id")
    val formativeFieldId: Int,
    @SerializedName("name_formative_field")
    val nameFormativeField: String,
    @SerializedName("list_of_works")
    val listWorks: List<ResponseListWork>,
)

data class ResponseListWork(
    @SerializedName("id")
    val workId: Int,
    @SerializedName("name")
    val workName: String,
    @SerializedName("list_of_work_student")
    val listWorks:  List<ResponseListWorkStudent>,
)


data class ResponseListWorkStudent(
    @SerializedName("id")
    val workStudentId: Int,
    @SerializedName("name")
    val workStudentName: String,
    @SerializedName("work_date")
    val workStudentDate: String?,
)
