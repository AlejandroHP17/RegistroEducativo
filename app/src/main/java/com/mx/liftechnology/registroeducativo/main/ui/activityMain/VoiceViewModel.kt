package com.mx.liftechnology.registroeducativo.main.ui.activityMain

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.util.VoiceRecognitionManager
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent

class VoiceViewModel(
    private val voiceRecognitionManager: VoiceRecognitionManager
) : ViewModel() {

    val results: LiveData<List<String>> = voiceRecognitionManager.resultsLiveData
    val error: LiveData<String> = voiceRecognitionManager.errorLiveData

    private val _changeButtonVoice = SingleLiveEvent<Int>()
    val changeButtonVoice: LiveData<Int> get() = _changeButtonVoice

    private var isListening = false

    private fun startListening() {
        voiceRecognitionManager.startListening()
    }

    private fun stopListening() {
        voiceRecognitionManager.stopListening()
    }

    override fun onCleared() {
        super.onCleared()
        voiceRecognitionManager.release()
    }

    fun change(context: Context) {
        if (isListening) {
            // Si está escuchando, detener la escucha
            startListening()
            isListening = false
            _changeButtonVoice.postValue(context.resources.getColor(R.color.color_error))

        } else {
            // Si no está escuchando, iniciar la escucha
            stopListening()
            isListening = true
            _changeButtonVoice.postValue(context.resources.getColor(R.color.color_success))
        }
    }
}