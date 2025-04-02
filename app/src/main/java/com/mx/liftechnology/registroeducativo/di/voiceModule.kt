package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.util.VoiceRecognitionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/** DI
 * @author pelkidev
 * @since 1.0.0
 */
val voiceModule = module {
    single { VoiceRecognitionManager(androidContext()) }
}