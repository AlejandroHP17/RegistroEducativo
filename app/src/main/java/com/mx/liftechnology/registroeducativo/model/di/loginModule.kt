package com.mx.liftechnology.registroeducativo.model.di

import androidx.lifecycle.ViewModelProvider
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.mx.liftechnology.core.network.callapi.LoginCallApi
import com.mx.liftechnology.core.network.enviroment.Environment
import com.mx.liftechnology.data.repository.flowLogin.LoginRepository
import com.mx.liftechnology.data.repository.flowLogin.LoginRepositoryImp
import com.mx.liftechnology.domain.usecase.MenuUseCase
import com.mx.liftechnology.domain.usecase.flowlogin.LoginUseCase
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login.LoginViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val loginModule = module {

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
