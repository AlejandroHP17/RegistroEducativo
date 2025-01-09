package com.mx.liftechnology.registroeducativo.di

import android.content.Context
import com.mx.liftechnology.core.util.VoiceRecognitionManager
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.VoiceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module


val voiceModule = module {
    // Proveer VoiceRecognitionManager
    factory { (context: Context) -> VoiceRecognitionManager(context) }

    // Proveer ViewModel
    viewModel { (context: Context) -> VoiceViewModel(get { parametersOf(context) }) }
}