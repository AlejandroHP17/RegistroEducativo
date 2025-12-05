package com.mx.liftechnology.registroeducativo.main.util.navigation

import android.net.Uri
import com.google.gson.Gson
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.registroeducativo.main.model.student.StudentDomainPar

/**
 * Objeto centralizado que contiene todas las rutas de la aplicación organizadas por módulo.
 * Proporciona una estructura clara y mantenible para la navegación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object AppRoutes {

    /**
     * Rutas relacionadas con la autenticación (login, registro, recuperación de contraseña).
     */
    object Auth {
        const val LOGIN = "login"
        const val REGISTER_USER = "registerUser"
        const val FORGET_PASSWORD = "forgetPassword"
    }

    /**
     * Rutas relacionadas con el flujo principal de la aplicación.
     */
    object Main {
        const val MENU = "menu"
        const val MENU_WITH_RELOAD = "menu?reload={reload}"
        const val PROFILE = "profile"
        const val CALENDAR = "calendar"
        
        // Rutas de estudiantes
        const val LIST_STUDENT = "listStudent"
        const val REGISTER_STUDENT = "registerStudent?student={student}"
        
        // Rutas de materias formativas
        const val LIST_FORMATIVE_FIELDS = "listFormativeFields"
        const val REGISTER_FORMATIVE_FIELD = "registerFormativeFields"
        
        // Rutas de ciclos escolares
        const val REGISTER_SCHOOL = "registerSchool"
        const val REGISTER_PARTIAL = "registerPartial"
        
        // Rutas de asignaciones
        const val WOTYFOFI_STUDENT = "wotyfofi?student={student}"
        const val ASSIGNMENT_FORMATIVE_FIELD = "assignment?formativeField={formativeField}"
        const val REGISTER_ASSIGNMENT = "registerassignment?formativeField={formativeField}"
        
        // Ruta de control de APIs
        const val API_CONTROL = "apiControl"

        /**
         * Crea la ruta del menú con el parámetro de recarga.
         *
         * @param reload Indica si se debe recargar el menú.
         * @return La ruta completa con el parámetro de recarga.
         */
        fun menuWithReload(reload: Boolean = false): String {
            return "$MENU?reload=$reload"
        }

        /**
         * Crea la ruta para la pantalla de registro de estudiante, pasando un objeto [StudentDomain] como parámetro.
         *
         * @param student El objeto estudiante a pasar (opcional, para edición).
         * @return La ruta completa con el estudiante serializado en formato JSON.
         */
        fun registerStudent(student: StudentDomainPar?): String {
            val studentJson = student?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "registerStudent?student=$studentJson"
        }

        /**
         * Crea la ruta para la pantalla de asignación de estudiante, pasando un objeto [StudentDomain] como parámetro.
         *
         * @param student El objeto estudiante a pasar.
         * @return La ruta completa con el estudiante serializado en formato JSON.
         */
        fun assignmentStudent(student: StudentDomainPar?): String {
            val studentJson = student?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "assignment?student=$studentJson"
        }

        /**
         * Crea la ruta para la pantalla de asignación de materia, pasando un objeto [FormativeFieldDomainPar] como parámetro.
         *
         * @param formativeField El objeto materia a pasar.
         * @return La ruta completa con la materia serializada en formato JSON.
         */
        fun assignmentFormativeField(formativeField: FormativeFieldDomainPar?): String {
            val formativeFieldJson = formativeField?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "assignment?formativeField=$formativeFieldJson"
        }

        /**
         * Crea la ruta para la pantalla de registro de asignación, pasando un objeto [FormativeFieldDomainPar] como parámetro.
         *
         * @param formativeField El objeto materia a pasar.
         * @return La ruta completa con la materia serializada en formato JSON.
         */
        fun registerAssignment(formativeField: FormativeFieldDomainPar?): String {
            val formativeFieldJson = formativeField?.let { Uri.encode(Gson().toJson(it)) } ?: ""
            return "registerassignment?formativeField=$formativeFieldJson"
        }
    }

    /**
     * Ruta para la pantalla de splash.
     */
    object Splash {
        const val SPLASH = "splash"
    }

    /**
     * Ruta para la pantalla de splash.
     */
    object Splash {
        const val SPLASH = "splash"
    }
}

