package com.mx.liftechnology.registroeducativo.main.util.navigation

import android.net.Uri
import com.google.gson.Gson
import com.mx.liftechnology.domain.model.formativeFields.ModelFormatFormativeFieldsDomain
import com.mx.liftechnology.domain.model.student.ModelStudentDomain

/**
 * Clase sellada que representa las rutas para el flujo principal de la aplicación.
 * Cada objeto define una ruta específica y, en algunos casos, funciones para construir la ruta con parámetros.
 *
 * @property route La ruta base como un String.
 * @author Pelkidev
 * @version 1.0.0
 */
sealed class MainRoutes (val route: String) {

    /** Ruta para la pantalla del menú principal. */
    data object Menu : MainRoutes("menu?reload={reload}") {

        fun withReload(reload: Boolean = false): String {
            return "menu?reload=$reload"
        }
    }

    /** Ruta para la pantalla de registro de escuela. */
    data object RegisterSchool: MainRoutes("registerSchool")

    /** Ruta para la pantalla de lista de estudiantes. */
    data object ListStudent: MainRoutes("listStudent")

    /** Ruta para la pantalla de registro de materia. */
    data object RegisterFormativeField : MainRoutes("registerSubject")

    /** Ruta para la pantalla de lista de materias. */
    data object ListFormativeFields : MainRoutes("listSubject")

    /** Ruta para la pantalla de registro de parciales. */
    data object RegisterPartial: MainRoutes("registerPartial")

    /** Ruta para la pantalla de calendario. */
    data object Calendar: MainRoutes("calendar")

    /** Ruta para la pantalla de perfil de usuario. */
    data object Profile: MainRoutes("profile")

    /** Ruta para la pantalla de registro de estudiante. */
    data object RegisterStudent : MainRoutes("registerStudent?student={student}") {
        /**
         * Crea la ruta para la pantalla de registro de estudiante, pasando un objeto [ModelStudentDomain] como parámetro.
         *
         * @param student El objeto estudiante a pasar (opcional, para edición).
         * @return La ruta completa con el estudiante serializado en formato JSON.
         */
        fun createRoutes(student: ModelStudentDomain?): String {
            val studentJson = student?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "registerStudent?student=$studentJson"
        }
    }

    /** Ruta para la pantalla de asignación de estudiante. */
    data object AssignmentStudent: MainRoutes("assignment?student={student}") {
        /**
         * Crea la ruta para la pantalla de asignación de estudiante, pasando un objeto [ModelStudentDomain] como parámetro.
         *
         * @param student El objeto estudiante a pasar.
         * @return La ruta completa con el estudiante serializado en formato JSON.
         */
        fun createRoutes(student: ModelStudentDomain?): String {
            val studentJson = student?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "assignment?student=$studentJson"
        }
    }

    /** Ruta para la pantalla de asignación de materia. */
    data object AssignmentSubject : MainRoutes("assignment?subject={subject}") {
        /**
         * Crea la ruta para la pantalla de asignación de materia, pasando un objeto [ModelFormatFormativeFieldsDomain] como parámetro.
         *
         * @param subject El objeto materia a pasar.
         * @return La ruta completa con la materia serializada en formato JSON.
         */
        fun createRoutes(subject: ModelFormatFormativeFieldsDomain?): String {
            val subjectJson = subject?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "assignment?subject=$subjectJson"
        }
    }

    /** Ruta para la pantalla de registro de asignación. */
    data object RegisterAssignment : MainRoutes("registerassignment?subject={subject}") {
        /**
         * Crea la ruta para la pantalla de registro de asignación, pasando un objeto [ModelFormatFormativeFieldsDomain] como parámetro.
         *
         * @param subject El objeto materia a pasar.
         * @return La ruta completa con la materia serializada en formato JSON.
         */
        fun createRoutes(subject: ModelFormatFormativeFieldsDomain?): String {
            val subjectJson = subject?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "registerassignment?subject=$subjectJson"
        }
    }

}