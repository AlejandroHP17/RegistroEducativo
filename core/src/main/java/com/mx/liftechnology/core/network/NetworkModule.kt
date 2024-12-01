package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.network.enviroment.Environment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    // Inyecta el TokenProvider
    single { TokenProvider(get()) }

    // Configura el interceptor de logging de OkHttp
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        logging
    }

    // Configura el AuthInterceptor
    single { AuthInterceptor(get()) }

    // Configura el cliente OkHttp con los interceptores (logging + autenticación)
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>()) // Agregar el interceptor de logging
            .addInterceptor(get<AuthInterceptor>()) // Agregar el interceptor de autenticación
            .build()
    }

    // Configura Retrofit usando el cliente de OkHttp con el interceptor de logging y autenticación
    single {
        Retrofit.Builder()
            .baseUrl(Environment.URL_BASE) // Ajusta la URL de acuerdo a tu entorno
            .client(get()) // Usa el cliente de OkHttp configurado
            .addConverterFactory(GsonConverterFactory.create()) // Usa Gson como convertidor JSON
            .build()
    }

}
