package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.school

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.model.modelApi.CctSchool
import com.mx.liftechnology.core.model.modelApi.DataCCT
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.usecase.flowregisterdata.CCTUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.RegisterSchoolUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.ValidateFieldsRegisterUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.launch

class RegisterSchoolViewModel (
    private val dispatcherProvider: DispatcherProvider,
    private val cctUseCase: CCTUseCase,
    private val validateFieldsUseCase: ValidateFieldsRegisterUseCase,
    private val registerSchoolUseCase: RegisterSchoolUseCase
) : ViewModel() {

    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean,Int>>()
    val animateLoader: LiveData< ModelState<Boolean,Int>> get() = _animateLoader

    // Observer the response of service
    private val _responseCCT = SingleLiveEvent<ModelState<List<DataCCT?>?,String>>()
    private val responseCCT: LiveData<ModelState<List<DataCCT?>?,String>> get() = _responseCCT

    // Observer the cct field
    private val _schoolCctField = SingleLiveEvent<ModelState<CctSchool?, String>>()
    val schoolCctField: LiveData<ModelState<CctSchool?, String>> get() = _schoolCctField

    // Observer the cct field
    private val _allField = SingleLiveEvent<Boolean>()
    val allField: LiveData<Boolean> get() = _allField

    private var grade : String? = null
    private var group : String? = null
    private var cycle : String? = null

    /** Go to validate  the cct
     * @author pelkidev
     * @since 1.0.0
     * */
    fun getSchoolCCT(cct: String){
        viewModelScope.launch(dispatcherProvider.io)  {
            _animateLoader.postValue(LoaderState(true))

            runCatching {
                cctUseCase.getSchoolCCT(cct)
            }.onSuccess {
                _animateLoader.postValue(LoaderState(false))
                _schoolCctField.postValue(it)
            }.onFailure {
                _schoolCctField.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
                _animateLoader.postValue(LoaderState(false))
            }
        }
    }

    /** Save in viewModel the variable of grade
     * @author pelkidev
     * @since 1.0.0
     * */
    fun saveGrade(data:String){
        grade = data
    }

    /** Save in viewModel the variable of group
     * @author pelkidev
     * @since 1.0.0
     * */
    fun saveGroup(data:String){
        group = data
    }

    /** Save in viewModel the variable of group
     * @author pelkidev
     * @since 1.0.0
     * */
    fun saveCycle(data:String){
        cycle = data
    }


    fun validateFields(grade: String, group: String, cycle: String) {
        viewModelScope.launch(dispatcherProvider.io)  {

            val gradeState = validateFieldsUseCase.validateGrade(grade)
            val groupState = validateFieldsUseCase.validateGroup(group)
            val cycleState = validateFieldsUseCase.validateGroup(group)

            if (schoolCctField.value is SuccessState && gradeState is SuccessState
                && groupState is SuccessState && cycleState is SuccessState){
                registerSchoolUseCase.putNewSchool((schoolCctField.value as SuccessState<CctSchool?, String>).result, grade, group, cycle)
                _allField.postValue(true)
            }else{
                _allField.postValue(false)
            }
        }
    }

}