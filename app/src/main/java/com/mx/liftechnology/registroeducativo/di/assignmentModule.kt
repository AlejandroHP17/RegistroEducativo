package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.assignment.AssignmentStudentViewModel
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.formativeFields.assignment.AssignmentSubjectViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for assignment-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val assignmentModule = module {
    /**
     * Provides an instance of [AssignmentSubjectViewModel].
     */
    viewModelOf(::AssignmentSubjectViewModel)

    /**
     * Provides an instance of [AssignmentStudentViewModel].
     */
    viewModelOf(::AssignmentStudentViewModel)
}
