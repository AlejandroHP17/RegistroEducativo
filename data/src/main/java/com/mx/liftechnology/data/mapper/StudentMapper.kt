package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseEditStudent
import com.mx.liftechnology.core.network.api.ResponseGetListEvaluationsStudent
import com.mx.liftechnology.core.network.api.ResponseGetStudent
import com.mx.liftechnology.core.network.api.ResponseRegisterStudent
import com.mx.liftechnology.data.model.student.ModelEvaluationsStudent
import com.mx.liftechnology.data.model.student.StudentData

object StudentMapper {
    fun ResponseEditStudent.mapperToModelStudent(): StudentData{
        return StudentData(
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

    fun List<ResponseGetStudent>.mapperToModelListStudent(): List<StudentData>{
        return this.mapIndexed { index, student ->
            StudentData(
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

    fun ResponseRegisterStudent.mapperToModelStudent(): StudentData{
        return StudentData(
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