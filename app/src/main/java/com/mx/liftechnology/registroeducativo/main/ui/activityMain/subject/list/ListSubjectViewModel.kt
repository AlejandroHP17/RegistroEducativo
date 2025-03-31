package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.GetListSubjectUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelListSubjectUIState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.share.ModelCustomCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListSubjectViewModel(
    private val getListSubjectUseCase: GetListSubjectUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelListSubjectUIState())
    val uiState: StateFlow<ModelListSubjectUIState> = _uiState.asStateFlow()

    fun getSubjectCompose() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            runCatching {
                getListSubjectUseCase.getListSubject()
            }.onSuccess { state ->
                if (state is SuccessState) {
                    _uiState.update { it.copy(
                        subjectList = state.result,
                        subjectListUI = state.result.convertModelCustomCard()
                    ) }
                }
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun List<ModelFormatSubjectDomain>?.convertModelCustomCard():List<ModelCustomCard>{
        return this?.map {
            ModelCustomCard(
                id = it.position.toString(),
                numberId = "",
                nameCard = "${it.name}"
            )
        }?: emptyList()
    }


    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // Observer the animate loader
    private val _responseListSubject = SingleLiveEvent<ModelState<List<ModelFormatSubjectDomain>?, String>?>()
    val responseListSubject: LiveData<ModelState<List<ModelFormatSubjectDomain>?, String>?> get() = _responseListSubject

    fun getSubject() {
        viewModelScope.launch {
            runCatching {
                _animateLoader.postValue(LoaderState(true))
                getListSubjectUseCase.getListSubject()
            }.onSuccess {
                _responseListSubject.postValue(it)
                _animateLoader.postValue(LoaderState(false))
            }.onFailure {
                _responseListSubject.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
                _animateLoader.postValue(LoaderState(false))
            }
        }
    }

    fun getLocalListSubject() {
        responseListSubject.value?.let {
            _animateLoader.postValue(LoaderState(false))
            _responseListSubject.postValue(_responseListSubject.value)
        }
    }
}