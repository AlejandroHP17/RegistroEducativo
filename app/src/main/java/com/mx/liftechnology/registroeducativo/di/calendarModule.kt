package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.calendar.CalendarViewModel
import org.koin.core.module.dsl.viewModelOf

import org.koin.dsl.module

/**
 * Este módulo se encarga de proveer las instancias necesarias para la pantalla de calendario,
 * como el [CalendarViewModel].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val calendarModule = module {
    /**
     * Provee una instancia de  [CalendarViewModel].
     */
    viewModelOf(::CalendarViewModel)
}
