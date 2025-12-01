package com.mx.liftechnology.data.repository.student

import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.mapper.StudentMapper.mapperToModelEvaluationsStudent
import com.mx.liftechnology.data.model.student.ModelEvaluationsStudent
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

fun interface GetListEvaluationsStudentRepository{
    suspend fun executeGetListEvaluationsStudent(
        schoolCycleId: Int,
        partialId: Int,
        formativeFieldId: Int,
        workTypeId: Int,
        studentId: Int,
        workDate: String?,
        workDateFrom : String?,
        workDateTo: String?
    ): ModelResult<List<ModelEvaluationsStudent>, NetworkModelError>
}
class GetListEvaluationsStudentRepositoryImpl(
    private val studentApi: StudentApi
):GetListEvaluationsStudentRepository {
    override suspend fun executeGetListEvaluationsStudent(
        schoolCycleId: Int,
        partialId: Int,
        formativeFieldId: Int,
        workTypeId: Int,
        studentId: Int,
        workDate: String?,
        workDateFrom : String?,
        workDateTo: String?
    ): ModelResult<List <ModelEvaluationsStudent>, NetworkModelError> {
        return try{
            val response = studentApi.getListEvaluations(
                formativeFieldId = formativeFieldId,
                partialId = partialId,
                workTypeId = workTypeId,
                schoolCycleId = schoolCycleId,
                studentId = studentId,
                workDate = workDate,
                workDateFrom = workDateFrom,
                workDateTo = workDateTo,
            )
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToModelEvaluationsStudent())
                } ?: ErrorResult(NetworkModelError.EMPTY)
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        }catch (e:Exception){
            ErrorResult(NetworkException.handleException(e))
        }
    }
}