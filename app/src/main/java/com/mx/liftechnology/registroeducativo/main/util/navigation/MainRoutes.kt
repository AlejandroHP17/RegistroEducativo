package com.mx.liftechnology.registroeducativo.main.util.navigation

import android.net.Uri
import com.google.gson.Gson
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain

/**
 * A sealed class that represents the routes for the main flow.
 *
 * @property route The route string.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
sealed class MainRoutes (val route: String) {

    /** The main menu screen. */
    data object Menu : MainRoutes("menu?reload={reload}") {
        /**
         * Creates the route for the main menu screen.
         *
         * @param reload Whether to reload the data.
         * @return The route string.
         */
        fun withReload(reload: Boolean = false): String {
            return "menu?reload=$reload"
        }
    }

    /** The school registration screen. */
    data object RegisterSchool: MainRoutes("registerSchool")

    /** The student list screen. */
    data object ListStudent: MainRoutes("listStudent")

    /** The subject registration screen. */
    data object RegisterSubject: MainRoutes("registerSubject")

    /** The subject list screen. */
    data object ListSubject: MainRoutes("listSubject")

    /** The partial registration screen. */
    data object RegisterPartial: MainRoutes("registerPartial")

    /** The calendar screen. */
    data object Calendar: MainRoutes("calendar")

    /** The profile screen. */
    data object Profile: MainRoutes("profile")

    /** The student registration screen. */
    data object RegisterStudent : MainRoutes("registerStudent?student={student}") {
        /**
         * Creates the route for the student registration screen.
         *
         * @param student The student data.
         * @return The route string.
         */
        fun createRoutes(student: ModelStudentDomain?): String {
            val studentJson = student?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "registerStudent?student=$studentJson"
        }
    }

    /** The student assignment screen. */
    data object AssignmentStudent: MainRoutes("assignment?student={student}") {
        /**
         * Creates the route for the student assignment screen.
         *
         * @param student The student data.
         * @return The route string.
         */
        fun createRoutes(student: ModelStudentDomain?): String {
            val studentJson = student?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "assignment?student=$studentJson"
        }
    }

    /** The subject assignment screen. */
    data object AssignmentSubject : MainRoutes("assignment?subject={subject}") {
        /**
         * Creates the route for the subject assignment screen.
         *
         * @param subject The subject data.
         * @return The route string.
         */
        fun createRoutes(subject: ModelFormatSubjectDomain?): String {
            val subjectJson = subject?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "assignment?subject=$subjectJson"
        }
    }

    /** The assignment registration screen. */
    data object RegisterAssignment : MainRoutes("registerassignment?subject={subject}") {
        /**
         * Creates the route for the assignment registration screen.
         *
         * @param subject The subject data.
         * @return The route string.
         */
        fun createRoutes(subject: ModelFormatSubjectDomain?): String {
            val subjectJson = subject?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "registerassignment?subject=$subjectJson"
        }
    }

}