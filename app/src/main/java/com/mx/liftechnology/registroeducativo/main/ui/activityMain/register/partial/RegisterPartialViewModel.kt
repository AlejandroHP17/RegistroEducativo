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
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.RegisterListPartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.ValidateFieldsRegisterUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class RegisterPartialViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateFieldsUseCase: ValidateFieldsRegisterUseCase,
    private val registerListPartialUseCase: RegisterListPartialUseCase,
    private val getListPartialUseCase: GetListPartialUseCase,
) : ViewModel() {
    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // Observer the cct field
    private val _responseRegisterPartial = SingleLiveEvent<ModelState<List<String?>?, String>>()
    val responseRegisterPartial: LiveData<ModelState<List<String?>?, String>> get() = _responseRegisterPartial

    // Observer the period select by user
    private val _periodNumber = SingleLiveEvent<Int>()
    private val periodNumber: LiveData<Int> get() = _periodNumber

    // Observer the date selected by user
    private val _datePeriod = SingleLiveEvent<ModelDatePeriodDomain>()
    val datePeriod: LiveData<ModelDatePeriodDomain> get() = _datePeriod

    // Observer the period select by user
    private val _periodField = SingleLiveEvent<ModelState<String, String>>()
    val periodField: LiveData<ModelState<String, String>> get() = _periodField

    // Observer the date selected by user
    private val _adapterField = SingleLiveEvent<ModelState<String, String>>()
    val adapterField: LiveData<ModelState<String, String>> get() = _adapterField

    private val _getPartialField = SingleLiveEvent<ModelState<MutableList<ModelDatePeriodDomain>?, String>?>()
    val getPartialField: LiveData<ModelState<MutableList<ModelDatePeriodDomain>?, String>?> get() = _getPartialField

    /** Save in viewModel the variable of period
     * @author pelkidev
     * @since 1.0.0
     * */
    fun savePeriod(data: String?) {
        val periodNumber = data?.toIntOrNull() ?: 0
        _periodNumber.postValue(periodNumber)
    }

    /** initDatePicker - Build the calendar and can save the date selected
     * @author pelkidev
     * @since 1.0.0
     */
    fun initDatePicker(
        item: ModelDatePeriodDomain,
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
            _datePeriod.postValue(ModelDatePeriodDomain(item.position, converterTime(dateRange)))
        }

        dateRangePicker.show(parentFragmentManager, "dateRangePicker")
    }

    /** converterTime - Formatter the correct structure for the date
     * @author pelkidev
     * @since 1.0.0
     * @return String
     */
    private fun converterTime(dateRange: Pair<Long, Long>): String {
        // Format od the date
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

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

    fun validateFields(adapterPeriods: List<ModelDatePeriodDomain>) {
        viewModelScope.launch(dispatcherProvider.io) {

            val periodState = validateFieldsUseCase.validatePeriod(periodNumber.value)
            val adapterState = validateFieldsUseCase.validateAdapter(adapterPeriods)

            _periodField.postValue(periodState)
            _adapterField.postValue(adapterState)

            if (periodState is SuccessState && adapterState is SuccessState) {
                registerListPartial(adapterPeriods)
            }
        }
    }

    private fun registerListPartial(
        adapterPeriods: List<ModelDatePeriodDomain>
    ) {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                registerListPartialUseCase.registerListPartial(periodNumber.value, adapterPeriods)
            }.onSuccess {
                _animateLoader.postValue(LoaderState(false))
                _responseRegisterPartial.postValue(it)
            }.onFailure {
                _animateLoader.postValue(LoaderState(false))
                _responseRegisterPartial.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }

    /** Read */
    fun getListPartial(){
        viewModelScope.launch (dispatcherProvider.io) {
            runCatching {
                getListPartialUseCase.getListPartial()
            }.onSuccess {
                _getPartialField.postValue(it)
            }.onFailure {
                _animateLoader.postValue(LoaderState(false))
                _getPartialField.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }

    fun loaderState(visible: Boolean){
        _animateLoader.postValue(LoaderState(visible))
    }
}