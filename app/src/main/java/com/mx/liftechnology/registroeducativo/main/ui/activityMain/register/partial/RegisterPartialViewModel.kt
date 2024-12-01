package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.partial

import android.content.Context
import androidx.core.util.Pair
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.mx.liftechnology.domain.usecase.flowregisterdata.CCTUseCase
import com.mx.liftechnology.domain.usecase.flowregisterdata.RegisterSchoolUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.util.ModelDatePeriod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class RegisterPartialViewModel (
    private val registerSchoolUseCase: RegisterSchoolUseCase
) : ViewModel() {
    // Observer the period select by user
    private val _periodNumber = SingleLiveEvent<Int>()
    val periodNumber: LiveData<Int> get() = _periodNumber

    // Observer the date selected by user
    private val _datePeriod = SingleLiveEvent<ModelDatePeriod>()
    val datePeriod: LiveData<ModelDatePeriod> get() = _datePeriod

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
            .setTitleText(context?.getString(R.string.register_partial_calendar))
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
        viewModelScope.launch(Dispatchers.IO) {

            val periodState = registerSchoolUseCase.validatePeriod(periodNumber.toString())

            /*_emailField.postValue(emailState)
            _passField.postValue(passState)

            if (cctState is SuccessState && passState is SuccessState) {
                login(email, pass, remember)
            }*/

        }
    }

}