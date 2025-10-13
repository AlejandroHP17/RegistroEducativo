package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.util.VoiceRecognitionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin module for voice recognition dependencies.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val voiceModule = module {
    /**
     * Provides a singleton instance of [VoiceRecognitionManager].
     */
    single { VoiceRecognitionManager(androidContext()) }
}