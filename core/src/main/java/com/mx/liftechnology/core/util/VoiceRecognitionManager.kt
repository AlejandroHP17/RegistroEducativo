package com.mx.liftechnology.core.util

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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.Locale

/**
 * Manages voice recognition functionality.
 *
 * @property context The application context.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class VoiceRecognitionManager(private val context: Context) {

    private val _resultsLiveData = MutableLiveData<List<String>>()
    /** LiveData that emits the recognition results. */
    val resultsLiveData: LiveData<List<String>> get() = _resultsLiveData

    private val _errorLiveData = MutableLiveData<String>()
    /** LiveData that emits recognition errors. */
    val errorLiveData: LiveData<String> get() = _errorLiveData

    private var speechRecognizer: SpeechRecognizer? = null
    private var recognizerIntent: Intent? = null

    private var restartAttempts = 0
    private val maxRestartAttempts = 5
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
            _errorLiveData.postValue(msg)
            isListening = false

            when (error) {
                SpeechRecognizer.ERROR_NO_MATCH,
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                    if (restartAttempts < maxRestartAttempts) {
                        restartAttempts++
                        handler.postDelayed({ startListening() }, 350)
                    } else {
                        _errorLiveData.postValue("Máximos reintentos alcanzados.")
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
            _resultsLiveData.postValue(matches ?: emptyList())
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
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora...")
            }
        } catch (e: Exception) {
            _errorLiveData.postValue("Error al configurar el recognizer: ${e.message}")
        }
    }

    /**
     * Starts listening for voice input.
     */
    fun startListening() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            _errorLiveData.postValue("Permiso RECORD_AUDIO no concedido")
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
            _errorLiveData.postValue("No se pudo iniciar: ${e.message}")
            resetRecognizer()
        }
    }

    /**
     * Stops listening for voice input.
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
     * Releases the resources used by the speech recognizer.
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