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
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.model.ModelDatePeriod
import com.mx.liftechnology.domain.usecase.flowdata.partial.CreatePartialUseCase
import com.mx.liftechnology.domain.usecase.flowdata.partial.ReadPartialUseCase
import com.mx.liftechnology.domain.usecase.flowdata.school.ValidateFieldsRegisterUseCase
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
    private val createPartialUseCase: CreatePartialUseCase,
    private val readPartialUseCase: ReadPartialUseCase,
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
    private val _datePeriod = SingleLiveEvent<ModelDatePeriod>()
    val datePeriod: LiveData<ModelDatePeriod> get() = _datePeriod

    // Observer the period select by user
    private val _periodField = SingleLiveEvent<ModelState<Int, String>>()
    val periodField: LiveData<ModelState<Int, String>> get() = _periodField

    // Observer the date selected by user
    private val _adapterField = SingleLiveEvent<ModelState<Int, String>>()
    val adapterField: LiveData<ModelState<Int, String>> get() = _adapterField

    private val _getPartialField = SingleLiveEvent<ModelState<MutableList<ModelDatePeriod>?, String>?>()
    val getPartialField: LiveData<ModelState<MutableList<ModelDatePeriod>?, String>?> get() = _getPartialField

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

    fun validateFields(adapterPeriods: MutableList<ModelDatePeriod>?) {
        viewModelScope.launch(dispatcherProvider.io) {

            val periodState = validateFieldsUseCase.validatePeriod(periodNumber.value)
            val adapterState = validateFieldsUseCase.validateAdapter(adapterPeriods)

            _periodField.postValue(periodState)
            _adapterField.postValue(adapterState)

            if (periodState is SuccessState && adapterState is SuccessState) {
                registerPartial(adapterPeriods)
            }
        }
    }

    private fun registerPartial(
        adapterPeriods: MutableList<ModelDatePeriod>?
    ) {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                createPartialUseCase.createPartials(periodNumber.value, adapterPeriods)
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
    fun getPartial(){
        viewModelScope.launch (dispatcherProvider.io) {
            runCatching {
                readPartialUseCase.readPartials()
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