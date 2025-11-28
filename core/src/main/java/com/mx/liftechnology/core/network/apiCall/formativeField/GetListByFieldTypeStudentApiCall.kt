/**
 * @file Define la llamada a la API para obtener la lista de tipos de evaluación y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.formativeField

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
fun interface GetListByFieldTypeStudentApiCall{
    /**
     * Realiza la petición a la API para obtener la lista de tipos de evaluación.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de [ResponseGetListWorkType].
     */
    @GET(Environment.END_POINT_GET_FIELD_TYPE_STUDENTS)
    suspend fun callApi(
        @Query ("formative_field_id") formativeFieldId : Int,
        @Query ("work_type_id") workTypeId : Int,
        @Query ("work_name") workName : String?,
        @Query ("work_date") workDate : String?
    ): Response<ResponseGeneric<ResponseGetByFieldTypeStudent>>
}

/**
 * Modelo de datos para la respuesta de la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetByFieldTypeStudent(
    @SerializedName("formative_field_id")
    val formativeFieldId : Int,
    @SerializedName("formative_field_name")
    val formativeFieldName:String,
    @SerializedName("work_type_id")
    val workTypeId : Int,
    @SerializedName("work_type_name")
    val workTypeName : String,
    @SerializedName("works")
    val works : List<ResponseGetListByFieldTypeStudent>
)

data class ResponseGetListByFieldTypeStudent(
    @SerializedName("id")
    val workId : Int,
    @SerializedName("name")
    val workName:String,
    @SerializedName("work_date")
    val workDate : String?,
    @SerializedName("students")
    val listStudents : List<ResponseGetListByFieldStudent>,
)

data class ResponseGetListByFieldStudent(
    @SerializedName("student_id")
    val studentId : Int,
    @SerializedName("student_name")
    val studentName:String,
    @SerializedName("grade")
    val grade : String?,
)


