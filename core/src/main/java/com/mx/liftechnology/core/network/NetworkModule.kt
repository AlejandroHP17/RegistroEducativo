/**
 * @file Contiene el módulo de Koin para las dependencias de red.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.network.environment.Environment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Módulo de Koin para las dependencias relacionadas con la red, como Retrofit y OkHttpClient.
 * Configura e inyecta las instancias necesarias para la comunicación con la API.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val networkModule = module {

    /**
     * Provee una instancia singleton de [TokenProvider].
     */
    single { TokenProvider(get()) }

    /**
     * Provee una instancia singleton de [HttpLoggingInterceptor] para el registro de las peticiones.
     */
    single {
        val logging = HttpLoggingInterceptor { message ->
            if (!message.startsWith("<!DOCTYPE html>")) {
                timber.log.Timber.d(message)
            }
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        logging
    }

    /**
     * Provee una instancia singleton de [AuthInterceptor] para la autenticación.
     */
    single { AuthInterceptor(get()) }

    /**
     * Provee una instancia singleton de [OkHttpClient], configurado con los interceptores.
     */
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }

    /**
     * Provee una instancia singleton de [Retrofit].
     */
    single {
        Retrofit.Builder()
            .baseUrl(Environment.URL_BASE)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
