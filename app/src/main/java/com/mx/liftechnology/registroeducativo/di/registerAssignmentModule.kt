package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.registerassignment.RegisterAssignmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registerAssignmentModule = module {
    viewModel { RegisterAssignmentViewModel(get()) }
}
