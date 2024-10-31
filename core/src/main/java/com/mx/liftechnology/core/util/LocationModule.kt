package com.mx.liftechnology.core.util

import org.koin.dsl.module

val locationModule = module {

    single { LocationHelper(get()) }

}
