package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.ui.calendar.CalendarViewModel
import org.koin.core.module.dsl.viewModelOf

import org.koin.dsl.module

/**
 * Koin module for calendar-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val calendarModule = module {
    /**
     * Provides an instance of [CalendarViewModel].
     */
    viewModelOf(::CalendarViewModel)
}
