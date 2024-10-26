package com.mx.liftechnology.registroeducativo.model.di

import com.mx.liftechnology.core.network.callapi.LoginCallApi
import com.mx.liftechnology.core.network.enviroment.Environment
import com.mx.liftechnology.data.repository.flowLogin.LoginRepository
import com.mx.liftechnology.data.repository.flowLogin.LoginRepositoryImp
import com.mx.liftechnology.domain.usecase.flowlogin.LoginUseCase
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login.LoginViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val loginModule = module {

    // Configura el interceptor de logging de OkHttp
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Imprime el cuerpo de las solicitudes y respuestas
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    // Configura Retrofit usando el cliente de OkHttp con el interceptor de logging
    single {
        Retrofit.Builder()
            .baseUrl(Environment.URL_BASE) // Ajusta la URL de acuerdo a tu entorno
            .client(get()) // Usa el cliente de OkHttp con el interceptor
            .addConverterFactory(GsonConverterFactory.create()) // Usa Gson como convertidor JSON
            .build()
    }

    // Registra el servicio API en Koin
    factory { get<Retrofit>().create(LoginCallApi::class.java) }


    single <LoginRepository>{
        LoginRepositoryImp(get())
    }

    single {
        LoginUseCase(get())
    }

    viewModel {
        LoginViewModel(get())
    }
}
