package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


fun interface RegisterOneJobStudentApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_REGISTER_JOB)
    suspend fun callApi(
        @Body credentials: CredentialsRegisterOneJobStudent,
    ): Response<GenericResponse<List<String?>?>>
}

data class CredentialsRegisterOneJobStudent(
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("fecha")
    val date: String,
    @SerializedName("numero")
    val number: Int,
    @SerializedName("tipotrabajopecg_id")
    val typeJobPecgId: Int,
    @SerializedName("campoformativopecgporcentaje_id")
    val fieldPecgPercentId: Int,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("parcialciclogrupo_id")
    val partialCycleGroupId: Int,
    @SerializedName("diaparcialciclogrupo_id")
    val dayPartialCycleGroupId: Int,
    @SerializedName("alumnostrabajos")
    val studentJobs: List<CredentialStudentJobs>,
)

data class CredentialStudentJobs(
    @SerializedName("alumnoescuelaciclogrupo_id")
    val studentSchoolCycleGroupId: Int,
    @SerializedName("calificacion")
    val qualification: Float,
    @SerializedName("comentario")
    val comment: String,
)
