package com.mx.liftechnology.registroeducativo.main.util.navigation

import android.net.Uri
import com.google.gson.Gson
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain

sealed class MainRoutes (val route: String) {

    data object Menu: MainRoutes("menu")
    data object RegisterSchool: MainRoutes("registerSchool")
    data object ListStudent: MainRoutes("listStudent")
    data object RegisterSubject: MainRoutes("registerSubject")
    data object ListSubject: MainRoutes("listSubject")
    data object RegisterPartial: MainRoutes("registerPartial")
    data object Profile: MainRoutes("profile")

    data object RegisterStudent : MainRoutes("registerStudent?student={student}") {
        fun createRoutes(student: ModelStudentDomain?): String {
            val studentJson = student?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "registerStudent?student=$studentJson" // Se usa "" en vez de "null"
        }
    }

    data object Assignment : MainRoutes("assignment?subject={subject}") {
        fun createRoutes(subject: ModelFormatSubjectDomain?): String {
            val subjectJson = subject?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "assignment?subject=$subjectJson" // Se usa "" en vez de "null"
        }
    }

    data object RegisterAssignment : MainRoutes("registerassignment?subject={subject}") {
        fun createRoutes(subject: ModelFormatSubjectDomain?): String {
            val subjectJson = subject?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "registerassignment?subject=$subjectJson" // Se usa "" en vez de "null"
        }
    }

}