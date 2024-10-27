package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.network.enviroment.Environment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    // Configura el interceptor de logging de OkHttp
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    // Configura Retrofit usando el cliente de OkHttp con el interceptor de logging
    single {
        Retrofit.Builder()
            .baseUrl(Environment.URL_BASE) // Ajusta la URL de acuerdo a tu entorno
            .client(get()) // Usa el cliente de OkHttp
            .addConverterFactory(GsonConverterFactory.create()) // Usa Gson como convertidor JSON
            .build()
    }
}
