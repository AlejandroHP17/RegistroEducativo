package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.network.enviroment.Environment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Koin module for network-related dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val networkModule = module {

    /**
     * Provides a singleton instance of [TokenProvider].
     */
    single { TokenProvider(get()) }

    /**
     * Provides a singleton instance of [HttpLoggingInterceptor].
     */
    single {
        val logging = HttpLoggingInterceptor { message ->
            if (!message.startsWith("<!DOCTYPE html>")) {
                println(message)
            }
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        logging
    }

    /**
     * Provides a singleton instance of [AuthInterceptor].
     */
    single { AuthInterceptor(get()) }

    /**
     * Provides a singleton instance of [OkHttpClient].
     */
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }

    /**
     * Provides a singleton instance of [Retrofit].
     */
    single {
        Retrofit.Builder()
            .baseUrl(Environment.URL_BASE)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
