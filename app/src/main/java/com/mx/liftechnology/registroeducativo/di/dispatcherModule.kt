package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.util.DefaultDispatcherProvider
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import org.koin.dsl.module

val dispatcherModule = module {

    single<DispatcherProvider> { DefaultDispatcherProvider() }

}
