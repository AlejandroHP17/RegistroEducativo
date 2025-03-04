package com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelVoiceConstants
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterStudentBinding
import com.mx.liftechnology.registroeducativo.main.funextensions.log
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.VoiceViewModel
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import com.mx.liftechnology.registroeducativo.main.viewextensions.errorET
import com.mx.liftechnology.registroeducativo.main.viewextensions.showCustomToastSuccess
import com.mx.liftechnology.registroeducativo.main.viewextensions.successET
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.Calendar
import java.util.Locale

class RegisterStudentFragment : Fragment() {

    private var _binding: FragmentRegisterStudentBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val registerStudentViewModel: RegisterStudentViewModel by viewModel()
    private val voiceViewModel: VoiceViewModel by viewModel { parametersOf(requireContext()) }

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    companion object {
        const val DATE_FORMAT = "%04d-%02d-%02d"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationHandler = context as? AnimationHandler
        initView()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animationHandler = null
        _binding = null
    }

    /** initialView - Print the correct view, menu or empty state
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initView() {
        binding.apply {
            includeHeader.tvTitle.text = getString(R.string.register_student_name)
            includeHeader.tvInsert.text = getString(R.string.register_student_name_description)
            includeHeader.btnReturn.visibility = View.VISIBLE
            includeButton.btnAction.text = getString(R.string.register_student_register)
        }
    }

    private fun initListeners() {
        binding.apply {
            includeHeader.btnReturn.setOnClickListener { findNavController().popBackStack() }
            includeButton.btnAction.setOnClickListener {
                registerStudentViewModel.validateFields(
                    etName.text.toString(),
                    etLastName.text.toString(),
                    etSecondLastName.text.toString(),
                    etCurp.text.toString(),
                    tvBirthday.text.toString(),
                    etPhoneNumber.text.toString()
                )
            }
            includeButton.btnRecord.setOnClickListener { voiceViewModel.change(requireContext()) }
            llBirthday.setOnClickListener { showCalendar() }
        }
    }

    private fun initObservers() {
        registerStudentViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if (state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }
                else -> animationHandler?.hideLoadingAnimation()
            }
        }

        registerStudentViewModel.nameField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputName.successET()
                is ErrorUserState -> binding.inputName.errorET(state.result)
                else -> binding.inputName.errorET(ModelCodeInputs.ET_MISTAKE_FORMAT)
            }
        }

        registerStudentViewModel.lastNameField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputLastName.successET()
                is ErrorUserState -> binding.inputLastName.errorET(state.result)
                else -> binding.inputLastName.errorET(ModelCodeInputs.ET_MISTAKE_FORMAT)
            }
        }
        registerStudentViewModel.secondLastNameField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputSecondLastName.successET()
                is ErrorUserState -> binding.inputSecondLastName.errorET(state.result)
                else -> binding.inputSecondLastName.errorET(ModelCodeInputs.ET_MISTAKE_FORMAT)
            }
        }
        registerStudentViewModel.curpField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputCurp.successET()
                is ErrorUserState -> binding.inputCurp.errorET(state.result)
                else -> binding.inputCurp.errorET(ModelCodeInputs.ET_CURP_FORMAT_MISTAKE)
            }
        }
        registerStudentViewModel.birthdayField.observe(viewLifecycleOwner) { _ ->
            /*when (state) {
                is SuccessState -> binding.inp.successET()
                is ErrorUserState -> binding.inputCurp.errorET(state.result)
                else -> binding.inputCurp.errorET(ModelCodeInputs.ET_MISTAKE_EMAIL)
            }
*/
        }
        registerStudentViewModel.phoneNumberField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputPhoneNumber.successET()
                is ErrorUserState -> binding.inputPhoneNumber.errorET(state.result)
                else -> binding.inputPhoneNumber.errorET(ModelCodeInputs.ET_PHONE_NUMBER_FORMAT_MISTAKE)
            }
        }

        registerStudentViewModel.responseRegisterStudent.observe(viewLifecycleOwner) { state ->
            log(state.toString())
            when (state) {
                is SuccessState -> {
                    val nav = RegisterStudentFragmentDirections.actionRegisterStudentFragmentToListStudentFragment()
                    findNavController().navigate(nav)
                    showCustomToastSuccess(requireActivity(),state.result.toString())
                }
                is ErrorState -> {}
                else -> {}
            }
        }

        voiceViewModel.results.observe(viewLifecycleOwner) { data ->
            registerStudentViewModel.validateDataRecord(data)
        }

        registerStudentViewModel.fillFields.observe(viewLifecycleOwner) { data ->
            data?.forEach {
                when (it.key) {
                    ModelVoiceConstants.NAME -> binding.etName.setText(it.value)
                    ModelVoiceConstants.LAST_NAME -> binding.etLastName.setText(it.value)
                    ModelVoiceConstants.SECOND_LAST_NAME -> binding.etSecondLastName.setText(it.value)
                    ModelVoiceConstants.CURP -> binding.etCurp.setText(it.value)
                    ModelVoiceConstants.BIRTHDAY -> binding.tvBirthday.text = it.value
                    ModelVoiceConstants.PHONE_NUMBER -> binding.etPhoneNumber.setText(it.value)
                }
            }
        }

        voiceViewModel.changeButtonVoice.observe(viewLifecycleOwner) { color ->
            binding.includeButton.btnRecord.setBackgroundColor(color)
        }
    }

    private fun showCalendar() {
        // Obtener la fecha actual
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        // Crear y mostrar el DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Formatear la fecha seleccionada en formato yyyy-MM-dd
                val fechaSeleccionada = String.format(
                    Locale.getDefault(), // Usar el locale predeterminado
                    DATE_FORMAT,   // Formato yyyy-MM-dd
                    selectedYear,       // Año
                    selectedMonth + 1,   // Mes (se suma 1 porque en Calendar enero es 0)
                    selectedDay          // Día
                )
                binding.tvBirthday.text = fechaSeleccionada
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}