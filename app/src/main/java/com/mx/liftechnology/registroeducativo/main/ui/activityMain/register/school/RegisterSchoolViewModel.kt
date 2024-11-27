package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.school

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.usecase.flowregisterdata.CCTUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.RegisterSchoolUseCase
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterSchoolViewModel (
    private val cctUseCase: CCTUseCase,
    private val registerSchoolUseCase: RegisterSchoolUseCase
) : ViewModel() {
    // Controlled coroutine
    private val coroutine = CoroutineScopeManager()

    // Observer the response of service
    private val _responseCCT = SingleLiveEvent<ModelState<List<DataCCT?>?>>()
    private val responseCCT: LiveData<ModelState<List<DataCCT?>?>> get() = _responseCCT

    // Observer the cct field
    private val _cctField = SingleLiveEvent<ModelState<Int>>()
    val cctField: LiveData<ModelState<Int>> get() = _cctField

    private var grade : String? = null
    private var group : String? = null

    /** Get the CCT, service
     * In correct case, make the request
     * @author pelkidev
     * @since 1.0.0
     * */
    fun getCCT() {
        coroutine.scopeIO.launch {
            runCatching {
                cctUseCase.getCCT()
            }.onSuccess {
                when (it) {
                    is SuccessState -> {
                        _responseCCT.postValue(SuccessState(it.result.data))
                    }

                    else -> {
                        _responseCCT.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
                    }
                }
            }.onFailure {
                _responseCCT.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
            }
        }
    }

    /** Go to validate  the cct
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateCCT(cct: String?){
        val cctState = cctUseCase.validateCCT(cct, responseCCT)
        _cctField.postValue(cctState)
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
        coroutine.scopeIO.launch {

            val cctState = cctField
            val gradeState = registerSchoolUseCase.validateGrade(grade)
            val groupState = registerSchoolUseCase.validateGroup(group)

            /*_emailField.postValue(emailState)
            _passField.postValue(passState)

            if (cctState is SuccessState && passState is SuccessState) {
                login(email, pass, remember)
            }*/

        }
    }

}