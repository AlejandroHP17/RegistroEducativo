/**
 * @file Contiene el módulo de Koin para las dependencias de red.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.util

import com.mx.liftechnology.core.BuildConfig
import com.mx.liftechnology.core.network.environment.Environment
import com.mx.liftechnology.core.network.interceptor.AuthInterceptor
import com.mx.liftechnology.core.network.interceptor.ConnectionErrorInterceptor
import com.mx.liftechnology.core.network.interceptor.ErrorHandlingInterceptor
import com.mx.liftechnology.core.util.extension.logDebug
import com.mx.liftechnology.core.util.session.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
    singleOf(::TokenProvider)

    /**
     * Provee una instancia singleton de [HttpLoggingInterceptor] para el registro de las peticiones.
     * El nivel de logging se configura según el tipo de build: BODY en DEBUG, NONE en producción.
     */
    single {
        val logging = HttpLoggingInterceptor { message ->
            if (!message.startsWith("<!DOCTYPE html>")) {
                logDebug(message)
            }
        }.apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        logging
    }

    /**
     * Provee una instancia singleton de [AuthInterceptor] para la autenticación.
     */
    singleOf (::AuthInterceptor)

    /**
     * Provee una instancia singleton de [ConnectionErrorInterceptor] para diagnóstico y logging de conectividad.
     * Este interceptor loguea información detallada de las peticiones y captura errores de conexión (IOException)
     * para facilitar el debugging de problemas de red de bajo nivel.
     */
    singleOf (::ConnectionErrorInterceptor)

    /**
     * Provee una instancia singleton de [ErrorHandlingInterceptor] para manejo centralizado de errores HTTP.
     */
    singleOf (::ErrorHandlingInterceptor)

    /**
     * Provee una instancia singleton de [OkHttpClient], configurado con los interceptores.
     * Los timeouts se configuran usando las constantes definidas en [NetworkConfig].
     * 
     * Orden de interceptores:
     * 1. AuthInterceptor - Maneja autenticación y refresh token
     * 2. HttpLoggingInterceptor - Loguea peticiones y respuestas HTTP
     * 3. ErrorHandlingInterceptor - Maneja y categoriza errores HTTP (4xx, 5xx)
     * 4. ConnectionErrorInterceptor - Diagnostica errores de conectividad (IOException) y loguea información de red
     */
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<ConnectionErrorInterceptor>())
            .addInterceptor(get<ErrorHandlingInterceptor>())
            .connectTimeout(NetworkConfig.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NetworkConfig.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NetworkConfig.WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Provee una instancia singleton de [Retrofit].
     */
    single {
        val baseUrl = Environment.URL_BASE
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    singleOf (::SessionManager)

}
