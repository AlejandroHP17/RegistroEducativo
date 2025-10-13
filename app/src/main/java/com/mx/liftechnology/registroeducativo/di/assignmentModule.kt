package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.assignment.AssignmentStudentViewModel
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.assignment.AssignmentSubjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
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
    viewModel { AssignmentSubjectViewModel(get(), get(),  get()) }

    /**
     * Provides an instance of [AssignmentStudentViewModel].
     */
    viewModel { AssignmentStudentViewModel(get(), get(),  get()) }
}
