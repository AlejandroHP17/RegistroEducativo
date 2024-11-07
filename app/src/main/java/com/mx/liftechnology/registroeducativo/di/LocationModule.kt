package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.util.LocationHelper
import org.koin.dsl.module

/** DI to login
 * @author pelkidev
 * @since 1.0.0
 */
val locationModule = module {

    single { LocationHelper(get()) }

}
