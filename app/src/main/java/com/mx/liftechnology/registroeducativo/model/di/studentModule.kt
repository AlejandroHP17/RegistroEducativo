package com.mx.liftechnology.registroeducativo.model.di

import androidx.room.Room
import com.mx.liftechnology.data.local.db.StudentRoomDatabase
import com.mx.liftechnology.data.repository.StudentLocalRepository
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.StudentViewModel
import com.mx.liftechnology.domain.usecase.StudentUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
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
        StudentLocalRepository(get())
    }

    single {
        StudentUseCase(get())
    }

    viewModel {
        StudentViewModel(get())
    }
}

