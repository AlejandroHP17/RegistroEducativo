package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.student.assignment.AssignmentStudentViewModel
import com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.subject.assignment.AssignmentSubjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val assignmentModule = module {
    viewModel { AssignmentSubjectViewModel(get(), get(),  get()) }
    viewModel { AssignmentStudentViewModel(get(), get(),  get()) }
}
