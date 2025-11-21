package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.apiCall.student.ResponseEditStudent
import com.mx.liftechnology.core.network.apiCall.student.ResponseGetListEvaluationsStudent
import com.mx.liftechnology.core.network.apiCall.student.ResponseGetStudent
import com.mx.liftechnology.core.network.apiCall.student.ResponseRegisterStudent
import com.mx.liftechnology.data.model.student.ModelEvaluationsStudent
import com.mx.liftechnology.data.model.student.ModelStudentData

object StudentDataToDomainMapper {
    fun ResponseEditStudent.mapperToModelStudent(): ModelStudentData{
        return ModelStudentData(
            curp = curp,
            name = name,
            lastName = lastName,
            secondLastName = secondLastName,
            birthday = birthday,
            phoneNumber = phoneNumber,
            userId = studentId,
            studentId = studentId,
            isActive = isActive
        )
    }

    fun List<ResponseGetStudent>.mapperToModelListStudent(): List<ModelStudentData>{
        return this.mapIndexed { index, student ->
            ModelStudentData(
                curp = student.curp,
                name = student.name,
                lastName = student.lastName,
                secondLastName = student.secondLastName,
                birthday = student.birthday,
                phoneNumber = student.phoneNumber,
                userId = student.userId,
                studentId = student.studentId,
                isActive = student.isActive
            )
        }
    }

    fun ResponseRegisterStudent.mapperToModelStudent(): ModelStudentData{
        return ModelStudentData(
            curp = curp,
            name = name,
            lastName = lastName,
            secondLastName = secondLastName,
            birthday = birthday,
            phoneNumber = phoneNumber,
            userId = studentId,
            studentId = studentId,
            isActive = isActive
        )
    }

    fun List<ResponseGetListEvaluationsStudent>.mapperToModelEvaluationsStudent(): List <ModelEvaluationsStudent>{
        return this.mapIndexed { index, student ->
            ModelEvaluationsStudent(
                studentId = student.studentId,
                evaluationName = student.evaluationName,
                grade = student.grade,
                workDate = student.workDate,
                evaluationId = student.evaluationId
            )
        }
    }
}