package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.model.ModelAdapterMenu
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.domain.usecase.MenuUseCase
import kotlinx.coroutines.launch

/** MenuViewModel - Control the data of the menu
 * @author pelkidev
 * @since 1.0.0
 * @param useCase Access to UseCase with DI
 */
class MenuViewModel(
    private val useCase: MenuUseCase
) : ViewModel() {

    // Controlled coroutine
    private val coroutine = CoroutineScopeManager()

    // Help to know the name of actual course, fill with preference
    private val _nameCourse = SingleLiveEvent<String>()
    val nameCourse: LiveData<String> = _nameCourse

    // List the option from menu
    private val _nameMenu = MutableLiveData<ModelState<List<ModelAdapterMenu>>>()
    val nameMenu: LiveData<ModelState<List<ModelAdapterMenu>>> = _nameMenu

    /** saveNameCourse - update the name of the course
     * @author pelkidev
     * @since 1.0.0
     */
    fun saveNameCourse(value: String) {
        _nameCourse.value = value
    }

    /** getMenu - Get all the options from menu, or a mistake in case
     * @author pelkidev
     * @since 1.0.0
     */
    fun getMenu() {
        coroutine.scopeIO.launch {
            runCatching {
                useCase.getMenu()
            }.onSuccess {
                _nameMenu.postValue(it)
            }.onFailure {
                _nameMenu.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
            }
        }
    }
}