package com.mx.liftechnology.registroeducativo.model.di

import androidx.room.Room
import com.mx.liftechnology.registroeducativo.data.local.dao.StudentDao
import com.mx.liftechnology.registroeducativo.data.local.db.StudentRoomDatabase
import com.mx.liftechnology.registroeducativo.data.local.repository.MenuRepository
import com.mx.liftechnology.registroeducativo.data.local.repository.StudentLocalRepository
import com.mx.liftechnology.registroeducativo.main.ui.student.StudentViewModel
import com.mx.liftechnology.registroeducativo.model.usecase.MenuUseCase
import com.mx.liftechnology.registroeducativo.model.usecase.StudentUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val studentModule = module {
    /* Local */
    single {
        // Crear o obtener una instancia de la base de datos Room
        val database = Room.databaseBuilder(
            get(),
            StudentRoomDatabase::class.java,
            "student_database"
        ).build()
        database.StudentDao() // Proveer StudentDao desde la base de datos
    }

    single {
        StudentLocalRepository(get()) // Inyectar StudentDao en StudentLocalRepository
    }

    single {
        StudentUseCase(get())
    }

    viewModel {
        StudentViewModel(get())
    }
}

