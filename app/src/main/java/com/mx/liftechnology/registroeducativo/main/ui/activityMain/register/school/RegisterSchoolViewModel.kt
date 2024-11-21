package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.school

import android.content.Context
import androidx.core.util.Pair
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.usecase.flowregisterdata.CCTUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.RegisterSchoolUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.util.ModelDatePeriod
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class RegisterSchoolViewModel (
    private val cctUseCase: CCTUseCase,
    private val registerSchoolUseCase: RegisterSchoolUseCase
) : ViewModel() {
    // Controlled coroutine
    private val coroutine = CoroutineScopeManager()

    // Observer the response of service
    private val _responseCCT = SingleLiveEvent<ModelState<List<DataCCT?>?>>()
    private val responseCCT: LiveData<ModelState<List<DataCCT?>?>> get() = _responseCCT

    // Observer the period select by user
    private val _periodNumber = SingleLiveEvent<Int>()
    val periodNumber: LiveData<Int> get() = _periodNumber

    // Observer the date selected by user
    private val _datePeriod = SingleLiveEvent<ModelDatePeriod>()
    val datePeriod: LiveData<ModelDatePeriod> get() = _datePeriod

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

    /** Save in viewModel the variable of period
     * @author pelkidev
     * @since 1.0.0
     * */
    fun savePeriod(data:String?){
        val periodNumber = data?.toIntOrNull() ?: 0
        _periodNumber.postValue(periodNumber)
    }

    /** initDatePicker - Build the calendar and can save the date selected
     * @author pelkidev
     * @since 1.0.0
     */
    fun initDatePicker(
        item: ModelDatePeriod,
        parentFragmentManager: FragmentManager,
        context: Context?
    ) {

        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText(context?.getString(R.string.register_school_calendar))
            .setPositiveButtonText(context?.getString(R.string.save))
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        dateRangePicker.addOnPositiveButtonClickListener { dateRange ->
            converterTime(dateRange)
            _datePeriod.postValue(ModelDatePeriod(item.position, converterTime(dateRange)))
             }

        dateRangePicker.show(parentFragmentManager, "dateRangePicker")
    }

    /** converterTime - Formatter the correct structure for the date
     * @author pelkidev
     * @since 1.0.0
     * @return String
     */
    private fun converterTime(dateRange: Pair<Long, Long>):String {
        // Format od the date
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        /** Convert both dates, start and end*/
        val startDate = Instant.ofEpochMilli(dateRange.first)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(formatter)

        val endDate = Instant.ofEpochMilli(dateRange.second)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(formatter)

        // join the strings
        return "$startDate  /  $endDate"
    }

    fun validateFields(shift: String) {
        coroutine.scopeIO.launch {

            val cctState = cctField
            val gradeState = registerSchoolUseCase.validateGrade(grade)
            val groupState = registerSchoolUseCase.validateGroup(group)
            val periodState = registerSchoolUseCase.validatePeriod(periodNumber.toString())

            /*_emailField.postValue(emailState)
            _passField.postValue(passState)

            if (cctState is SuccessState && passState is SuccessState) {
                login(email, pass, remember)
            }*/

        }
    }

}