package com.mx.liftechnology.registroeducativo.di

import com.mx.liftechnology.core.util.voice.VoiceRecognitionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Módulo de Koin para dependencias relacionadas con el reconocimiento de voz.
 * 
 * Este módulo proporciona las instancias necesarias para:
 * - Gestión del reconocimiento de voz del dispositivo
 * - Conversión de voz a texto
 * - Validación de entrada por voz
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val voiceModule = module {
    /**
     * Proporciona una instancia singleton de [VoiceRecognitionManager].
     * Manager para gestionar el reconocimiento de voz del dispositivo.
     */
    single { VoiceRecognitionManager(androidContext()) }
}