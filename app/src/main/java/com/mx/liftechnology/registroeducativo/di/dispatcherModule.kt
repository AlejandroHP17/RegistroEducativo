package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.registroeducativo.main.util.DefaultDispatcherProvider
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import org.koin.dsl.module

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val dispatcherModule = module {
    single<DispatcherProvider>{ DefaultDispatcherProvider() }
}
