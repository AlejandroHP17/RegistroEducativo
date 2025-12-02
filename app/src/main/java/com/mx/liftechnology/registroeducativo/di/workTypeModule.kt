package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.network.api.WorkTypeApi
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * @author Pelkidev
 * @version 1.0.0
 */
val workTypeModule = module {
    /**
     * Provides an instance of [WorkTypeApi].
     */
    factory { get<Retrofit>().create(WorkTypeApi::class.java) }
}