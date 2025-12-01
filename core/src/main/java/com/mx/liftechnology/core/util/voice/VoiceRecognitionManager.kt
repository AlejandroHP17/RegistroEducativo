package com.mx.liftechnology.core.util.voice

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

/**
 * Gestiona la funcionalidad de reconocimiento de voz.
 * Esta clase encapsula la lógica para iniciar, detener y procesar los resultados del `SpeechRecognizer`.
 *
 * @property context El contexto de la aplicación, necesario para acceder a los servicios de reconocimiento de voz.
 * @author Pelkidev
 * @version 1.0.0
 */
class VoiceRecognitionManager(private val context: Context) {

    companion object {
        /** Número máximo de reintentos cuando hay errores de reconocimiento. */
        private const val MAX_RESTART_ATTEMPTS = 5

        /** Delay en milisegundos antes de reintentar el reconocimiento después de un error. */
        private const val RESTART_DELAY_MS = 350L

        /** Número máximo de resultados a devolver. */
        private const val MAX_RESULTS = 3

        /** Mensaje de error cuando se alcanzan los máximos reintentos. */
        private const val MAX_RETRIES_MESSAGE = "Máximos reintentos alcanzados."
    }

    private val _resultsStateFlow = MutableStateFlow<List<String>>(emptyList())
    /** StateFlow que emite los resultados del reconocimiento. */
    val resultsStateFlow: StateFlow<List<String>> = _resultsStateFlow.asStateFlow()

    private val _errorStateFlow = MutableStateFlow<String?>(null)
    /** StateFlow que emite los errores del reconocimiento. */
    val errorStateFlow: StateFlow<String?> = _errorStateFlow.asStateFlow()

    private var speechRecognizer: SpeechRecognizer? = null
    private var recognizerIntent: Intent? = null

    private var restartAttempts = 0
    private val handler = Handler(Looper.getMainLooper())

    private var isListening = false

    init {
        setupRecognizer()
    }

    private val listener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {}

        override fun onBeginningOfSpeech() {}

        override fun onRmsChanged(rmsdB: Float) {}

        override fun onBufferReceived(buffer: ByteArray?) {}

        override fun onEndOfSpeech() {}

        override fun onError(error: Int) {
            val msg = getErrorText(error)
            _errorStateFlow.value = msg
            isListening = false

            when (error) {
                SpeechRecognizer.ERROR_NO_MATCH,
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                    if (restartAttempts < MAX_RESTART_ATTEMPTS) {
                        restartAttempts++
                        handler.postDelayed({ startListening() }, RESTART_DELAY_MS)
                    } else {
                        _errorStateFlow.value = MAX_RETRIES_MESSAGE
                    }
                }
                SpeechRecognizer.ERROR_CLIENT,
                SpeechRecognizer.ERROR_SERVER,
                SpeechRecognizer.ERROR_NETWORK,
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> {
                    resetRecognizer()
                }
                else -> {
                    resetRecognizer()
                }
            }
        }

        override fun onResults(results: Bundle?) {
            restartAttempts = 0
            isListening = false
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            _resultsStateFlow.value = matches ?: emptyList()
        }

        override fun onPartialResults(partialResults: Bundle?) {
            val partial = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        }

        override fun onEvent(eventType: Int, params: Bundle?) {}
    }

    private fun setupRecognizer() {
        try {
            val appCtx = context.applicationContext
            if (speechRecognizer == null) {
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(appCtx)
                speechRecognizer?.setRecognitionListener(listener)
            }

            recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, MAX_RESULTS)
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora...")
            }
        } catch (e: Exception) {
            _errorStateFlow.value = "Error al configurar el recognizer: ${e.message}"
        }
    }

    /**
     * Inicia la escucha de la entrada de voz.
     */
    fun startListening() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            _errorStateFlow.value = "Permiso RECORD_AUDIO no concedido"
            return
        }

        if (speechRecognizer == null || recognizerIntent == null) {
            setupRecognizer()
        }

        if (isListening) {
            try {
                speechRecognizer?.cancel()
            } catch (_: Exception) {}
            isListening = false
        }

        try {
            speechRecognizer?.startListening(recognizerIntent)
            isListening = true
        } catch (e: Exception) {
            _errorStateFlow.value = "No se pudo iniciar: ${e.message}"
            resetRecognizer()
        }
    }

    /**
     * Detiene la escucha de la entrada de voz.
     */
    fun stopListening() {
        try {
            speechRecognizer?.stopListening()
        } catch (e: Exception) {
        } finally {
            isListening = false
        }
    }

    private fun resetRecognizer() {
        try {
            handler.removeCallbacksAndMessages(null)
            speechRecognizer?.cancel()
        } catch (_: Exception) {}
        try {
            speechRecognizer?.destroy()
        } catch (_: Exception) {}
        speechRecognizer = null
        recognizerIntent = null
        isListening = false
        restartAttempts = 0
        setupRecognizer()
    }

    /**
     * Libera los recursos utilizados por el reconocedor de voz.
     */
    fun release() {
        handler.removeCallbacksAndMessages(null)
        try {
            speechRecognizer?.cancel()
        } catch (_: Exception) {}
        try {
            speechRecognizer?.destroy()
        } catch (_: Exception) {}
        speechRecognizer = null
        recognizerIntent = null
        isListening = false
    }

    private fun getErrorText(errorCode: Int): String {
        return when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> "ERROR_AUDIO"
            SpeechRecognizer.ERROR_CLIENT -> "ERROR_CLIENT"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "ERROR_INSUFFICIENT_PERMISSIONS"
            SpeechRecognizer.ERROR_NETWORK -> "ERROR_NETWORK"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "ERROR_NETWORK_TIMEOUT"
            SpeechRecognizer.ERROR_NO_MATCH -> "ERROR_NO_MATCH"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "ERROR_RECOGNIZER_BUSY"
            SpeechRecognizer.ERROR_SERVER -> "ERROR_SERVER"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "ERROR_SPEECH_TIMEOUT"
            else -> "ERROR_UNKNOWN"
        }
    }
}

