package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.assignment.AssignmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val assignmentModule = module {
    viewModel { AssignmentViewModel() }
}
