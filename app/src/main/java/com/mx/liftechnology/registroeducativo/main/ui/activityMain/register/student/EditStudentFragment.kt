package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.student

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterStudentBinding
import com.mx.liftechnology.registroeducativo.main.funextensions.log
import com.mx.liftechnology.registroeducativo.main.viewextensions.errorET
import com.mx.liftechnology.registroeducativo.main.viewextensions.successET
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import java.util.Locale

class EditStudentFragment : Fragment() {

    private var _binding: FragmentRegisterStudentBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val editStudentViewModel: EditStudentViewModel by viewModel()

    private val args: EditStudentFragmentArgs by navArgs()

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

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
        setDataToView()
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
            includeHeader.tvTitle.text = getString(R.string.edit_student_name)
            includeHeader.tvInsert.text = getString(R.string.register_student_name_description)
            includeHeader.btnReturn.visibility = View.VISIBLE
            includeButton.btnAction.text = getString(R.string.save)
        }
    }

    private fun setDataToView(){
        binding.apply {
            etName.setText(args.infoStudent.name)
            etLastName.setText(args.infoStudent.lastName)
            etSecondLastName.setText(args.infoStudent.secondLastName)
            etCurp.setText(args.infoStudent.curp)
            etPhoneNumber.setText(args.infoStudent.phoneNumber)
            tvBirthday.text = args.infoStudent.birthday
        }
    }

    private fun initListeners() {
        binding.apply {
            includeHeader.btnReturn.setOnClickListener { findNavController().popBackStack() }
            includeButton.btnAction.setOnClickListener {
                editStudentViewModel.validateFields(
                    etName.text.toString(),
                    etLastName.text.toString(),
                    etSecondLastName.text.toString(),
                    etCurp.text.toString(),
                    tvBirthday.text.toString(),
                    etPhoneNumber.text.toString()
                )
            }
            llBirthday.setOnClickListener { showCalendar() }
        }
    }


    private fun initObservers() {
        editStudentViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if (state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }
                else -> animationHandler?.hideLoadingAnimation()
            }
        }

        editStudentViewModel.nameField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputName.successET()
                is ErrorState -> binding.inputName.errorET(state.result)
                else -> binding.inputName.errorET(ModelCodeError.ET_MISTAKE_EMAIL)
            }
        }

        editStudentViewModel.lastNameField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputLastName.successET()
                is ErrorState -> binding.inputLastName.errorET(state.result)
                else -> binding.inputLastName.errorET(ModelCodeError.ET_MISTAKE_EMAIL)
            }
        }
        editStudentViewModel.secondLastNameField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputSecondLastName.successET()
                is ErrorState -> binding.inputSecondLastName.errorET(state.result)
                else -> binding.inputSecondLastName.errorET(ModelCodeError.ET_MISTAKE_EMAIL)
            }
        }
        editStudentViewModel.curpField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputCurp.successET()
                is ErrorState -> binding.inputCurp.errorET(state.result)
                else -> binding.inputCurp.errorET(ModelCodeError.ET_MISTAKE_EMAIL)
            }
        }
        editStudentViewModel.birthdayField.observe(viewLifecycleOwner) { _ ->
            /*when (state) {
                is SuccessState -> binding.inp.successET()
                is ErrorState -> binding.inputCurp.errorET(state.result)
                else -> binding.inputCurp.errorET(ModelCodeError.ET_MISTAKE_EMAIL)
            }
*/
        }
        editStudentViewModel.phoneNumberField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputPhoneNumber.successET()
                is ErrorState -> binding.inputPhoneNumber.errorET(state.result)
                else -> binding.inputPhoneNumber.errorET(ModelCodeError.ET_MISTAKE_EMAIL)
            }
        }

        editStudentViewModel.responseEditStudent.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    val direction =
                        RegisterStudentFragmentDirections.actionRegisterStudentFragmentToListStudentFragment()
                    findNavController().navigate(direction)
                }
                is ErrorState -> log("state $state")
                else -> log("state $state")
            }
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
                    "%04d-%02d-%02d",   // Formato yyyy-MM-dd
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