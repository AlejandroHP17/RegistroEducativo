package com.mx.liftechnology.registroeducativo.main.ui.activityMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.util.VoiceRecognitionManager

class VoiceViewModel(
    private val voiceRecognitionManager: VoiceRecognitionManager
) : ViewModel() {

    val results: LiveData<List<String>> = voiceRecognitionManager.resultsLiveData
    val error: LiveData<String> = voiceRecognitionManager.errorLiveData

    fun startListening() {
        voiceRecognitionManager.startListening()
    }

    fun stopListening() {
        voiceRecognitionManager.stopListening()
    }

    override fun onCleared() {
        super.onCleared()
        voiceRecognitionManager.release()
    }

}