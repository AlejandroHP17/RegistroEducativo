package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.school

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mx.liftechnology.core.network.callapi.ResponseCctSchool
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterSchoolBinding
import com.mx.liftechnology.registroeducativo.main.funextensions.log
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.VoiceViewModel
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import com.mx.liftechnology.registroeducativo.main.util.ModelSpinnerSelect
import com.mx.liftechnology.registroeducativo.main.viewextensions.errorET
import com.mx.liftechnology.registroeducativo.main.viewextensions.fillItem
import com.mx.liftechnology.registroeducativo.main.viewextensions.showCustomToastFailed
import com.mx.liftechnology.registroeducativo.main.viewextensions.showCustomToastSuccess
import com.mx.liftechnology.registroeducativo.main.viewextensions.successET
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/** RegisterPartialFragment - Accept the data of the school
 * @author pelkidev
 * @since 1.0.0
 */
class RegisterSchoolFragment : Fragment() {

    private var _binding: FragmentRegisterSchoolBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val registerSchoolViewModel: RegisterSchoolViewModel by viewModel()
    private val voiceViewModel: VoiceViewModel by viewModel { parametersOf(requireContext()) }

    /* loader variable */
    private var animationHandler: AnimationHandler? = null
    private var controlCCTClean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterSchoolBinding.inflate(inflater, container, false)
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
        initWatcher()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animationHandler = null
        _binding = null
    }

    /** initView - Build the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initView() {
        binding.apply {
            includeHeader.tvTitle.text = getString(R.string.register_school)
            includeHeader.tvInsert.text = getString(R.string.register_school_description)
            includeSpinnerSchool.tvDemostration.text = getString(R.string.form_school_name)
            includeSpinnerShift.tvDemostration.text = getString(R.string.form_school_shift)
            includeSpinnerType.tvDemostration.text = getString(R.string.form_school_type)
            includeSpinnerGrade.tvDemostration.text = getString(R.string.form_school_grade)
            includeSpinnerGroup.tvDemostration.text = getString(R.string.form_school_group)
            includeSpinnerCycle.tvDemostration.text = getString(R.string.form_school_term)
        }
    }

    /** initObservers - focus in the variables from viewmodel
     * @author pelkidev
     * @since 1.0.0
     * `cctField` check the cct and  fill other fields
     * `numberPerdiod` check the number of periods selected
     * `datePeriod` check the  date and post the date in correct view
     * */
    private fun initObservers() {
        registerSchoolViewModel.schoolCctField.observe(viewLifecycleOwner) { state ->
            binding.apply {
                when (state) {
                    is SuccessState -> {
                        inputCct.successET()
                        includeSpinnerSchool.tvDemostration.text = state.result?.schoolName
                        includeSpinnerShift.tvDemostration.text = state.result?.shift
                        includeSpinnerType.tvDemostration.text = state.result?.schoolCycleType
                        showLogicSpinner(state.result)
                    }
                    is ErrorState -> {
                        cleanAutoText()
                        inputCct.errorET(ModelCodeInputs.ET_MISTAKE_FORMAT)
                    }
                    is ErrorUserState -> {
                        cleanAutoText()
                        inputCct.errorET(ModelCodeInputs.ET_NOT_FOUND)
                    }
                    else -> {
                        inputCct.errorET(ModelCodeInputs.ET_EMPTY)
                        cleanAutoText()
                    }
                }
            }
        }

        registerSchoolViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if (state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }

                else -> animationHandler?.hideLoadingAnimation()
            }
        }

        registerSchoolViewModel.allField.observe(viewLifecycleOwner) { state ->
            if (!state) {
                showCustomToastFailed(
                    requireActivity(),
                    getString(R.string.toast_error_validate_fields)
                )
            }
        }

        registerSchoolViewModel.cct.observe(viewLifecycleOwner) { data ->
            binding.etCct.setText(data)
        }

        registerSchoolViewModel.responseRegisterSchool.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    showCustomToastSuccess(requireActivity(), state.result.toString())
                    findNavController().popBackStack()
                }
                is ErrorUserState -> showCustomToastFailed(requireActivity(), state.result)
                else -> log(state.toString())
            }
        }

        voiceViewModel.results.observe(viewLifecycleOwner) { state ->
            registerSchoolViewModel.validateData(state)
        }

        voiceViewModel.changeButtonVoice.observe(viewLifecycleOwner) { color ->
            binding.includeButton.btnRecord.setBackgroundColor(color)
        }
    }

    private fun showLogicSpinner(result: ResponseCctSchool?) {
        binding.apply {
            val grade = includeSpinnerGrade.spinner.fillItem(
                requireContext(),
                ModelSpinnerSelect.GRADE,
                result?.schoolType
            )
            val group = includeSpinnerGroup.spinner.fillItem(
                requireContext(),
                ModelSpinnerSelect.GROUP,
                result?.schoolType
            )
            val cycle = includeSpinnerCycle.spinner.fillItem(
                requireContext(),
                ModelSpinnerSelect.CYCLE,
                result?.schoolCycleType
            )
            registerSchoolViewModel.saveGrade(grade)
            registerSchoolViewModel.saveGroup(group)
            registerSchoolViewModel.saveCycle(cycle)

            includeSpinnerGrade.tvDemostration.visibility = View.GONE
            includeSpinnerGroup.tvDemostration.visibility = View.GONE
            includeSpinnerCycle.tvDemostration.visibility = View.GONE
            includeSpinnerGrade.spinner.visibility = View.VISIBLE
            includeSpinnerGroup.spinner.visibility = View.VISIBLE
            includeSpinnerCycle.spinner.visibility = View.VISIBLE
        }
    }

    /** initListeners - Build the click on the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initListeners() {
        binding.apply {
            includeHeader.btnReturn.setOnClickListener {
                findNavController().popBackStack()
            }

            includeButton.btnAction.setOnClickListener {
                registerSchoolViewModel.validateFields()
            }

            // Configurar el botón con el toggle
            includeButton.btnRecord.setOnClickListener {
                voiceViewModel.change(requireContext())
            }

            /** Spinner Section*/
            includeSpinnerGrade.spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedValue = parent?.getItemAtPosition(position).toString()
                        registerSchoolViewModel.saveGrade(selectedValue)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        //Nothing
                    }
                }
            includeSpinnerGroup.spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedValue = parent?.getItemAtPosition(position).toString()
                        registerSchoolViewModel.saveGroup(selectedValue)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        //Nothing
                    }
                }
            includeSpinnerCycle.spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedValue = parent?.getItemAtPosition(position).toString()
                        registerSchoolViewModel.saveCycle(selectedValue)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        //Nothing
                    }
                }
        }
    }

    /** initWatcher - Observe the cct field to validate if exist someone
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initWatcher() {
        binding.apply {
            etCct.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //Nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    var updatedText = s.toString().uppercase() // Convierte a mayúsculas.

                    // Elimina espacios y caracteres no válidos según regexDifferent.
                    updatedText = updatedText.filter { it.toString().matches(ModelRegex.CCT) }

                    // Solo actualiza el texto si es necesario.
                    if (s.toString() != updatedText) {
                        etCct.removeTextChangedListener(this) // Evita recursividad.
                        etCct.setText(updatedText) // Actualiza el texto.
                        etCct.setSelection(updatedText.length) // Mantén el cursor al final.
                        etCct.addTextChangedListener(this) // Vuelve a agregar el listener.
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    val input = s.toString()
                    if (input.length == 10) {
                        if (!controlCCTClean) controlCCTClean = true
                        registerSchoolViewModel.getSchoolCCT(input.uppercase())
                    } else if (controlCCTClean) {
                        controlCCTClean = false
                        cleanAutoText()
                    }
                }
            })
        }
    }

    /** cleanAutoText - In order to cct
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun cleanAutoText() {
        binding.apply {
            includeSpinnerSchool.tvDemostration.text = getString(R.string.form_school_name)
            includeSpinnerShift.tvDemostration.text = getString(R.string.form_school_shift)
            includeSpinnerType.tvDemostration.text = getString(R.string.form_school_type)
            includeSpinnerSchool.tvDemostration.visibility = View.VISIBLE
            includeSpinnerShift.tvDemostration.visibility = View.VISIBLE
            includeSpinnerType.tvDemostration.visibility = View.VISIBLE

            includeSpinnerGrade.tvDemostration.visibility = View.VISIBLE
            includeSpinnerGroup.tvDemostration.visibility = View.VISIBLE
            includeSpinnerCycle.tvDemostration.visibility = View.VISIBLE
            includeSpinnerGrade.spinner.visibility = View.GONE
            includeSpinnerGroup.spinner.visibility = View.GONE
            includeSpinnerCycle.spinner.visibility = View.GONE
        }
    }
}