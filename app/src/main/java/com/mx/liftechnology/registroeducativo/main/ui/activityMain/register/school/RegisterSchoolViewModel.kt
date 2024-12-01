package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.school

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.usecase.flowregisterdata.CCTUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.RegisterSchoolUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.ValidateFieldsRegisterUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterSchoolViewModel (
    private val cctUseCase: CCTUseCase,
    private val validateFieldsUseCase: ValidateFieldsRegisterUseCase,
    private val registerSchoolUseCase: RegisterSchoolUseCase
) : ViewModel() {
    // Observer the response of service
    private val _responseCCT = SingleLiveEvent<ModelState<List<DataCCT?>?,String>>()
    private val responseCCT: LiveData<ModelState<List<DataCCT?>?,String>> get() = _responseCCT

    // Observer the cct field
    private val _cctField = SingleLiveEvent<ModelState<Int, Int>?>()
    val cctField: LiveData<ModelState<Int, Int>?> get() = _cctField

    private var grade : String? = null
    private var group : String? = null

    /** Get the CCT, service
     * In correct case, make the request
     * @author pelkidev
     * @since 1.0.0
     * */
    fun getCCT() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                cctUseCase.getCCT()
            }.onSuccess {
                when (it) {
                    is SuccessState -> {
                        _responseCCT.postValue(SuccessState(it.result))
                    }

                    else -> {
                        _responseCCT.postValue(it)
                    }
                }
            }.onFailure {
                _responseCCT.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }

    /** Go to validate  the cct
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateCCT(cct: String?){
        viewModelScope.launch(Dispatchers.IO) {
            val cctState = cctUseCase.validateCCT(cct, responseCCT.value)
            _cctField.postValue(cctState)
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


    fun validateFields(shift: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val cctState = cctField
            val gradeState = validateFieldsUseCase.validateGrade(grade)
            val groupState = validateFieldsUseCase.validateGroup(group)

            /*_emailField.postValue(emailState)
            _passField.postValue(passState)

            if (cctState is SuccessState && passState is SuccessState) {
                login(email, pass, remember)
            }*/

        }
    }

}