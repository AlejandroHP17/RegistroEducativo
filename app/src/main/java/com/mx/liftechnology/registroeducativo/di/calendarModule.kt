package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.calendar.CalendarViewModel
import org.koin.core.module.dsl.viewModelOf

import org.koin.dsl.module

/**
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
