package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.school

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mx.liftechnology.core.model.modelApi.CctSchool
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelRegex
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.interfaces.AnimationHandler
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterSchoolBinding
import com.mx.liftechnology.registroeducativo.main.util.ModelSpinnerSelect
import com.mx.liftechnology.registroeducativo.main.viewextensions.errorET
import com.mx.liftechnology.registroeducativo.main.viewextensions.fillItem
import com.mx.liftechnology.registroeducativo.main.viewextensions.showCustomToastFailed
import com.mx.liftechnology.registroeducativo.main.viewextensions.successET
import org.koin.androidx.viewmodel.ext.android.viewModel

/** RegisterPartialFragment - Accept the data of the school
 * @author pelkidev
 * @since 1.0.0
 */
class RegisterSchoolFragment : Fragment() {

    private var _binding: FragmentRegisterSchoolBinding? = null
    private val binding get() = _binding!!

    private val registerSchoolViewModel: RegisterSchoolViewModel by viewModel()

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    /**
     * block to accept the animation if the fragment is attached to the activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        animationHandler = context as? AnimationHandler
    }
    override fun onDetach() {
        super.onDetach()
        animationHandler = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterSchoolBinding.inflate(inflater, container, false)
        initView()
        initListener()
        initObserver()
        initWatcher()

        return binding.root
    }

    /** initView - Build the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initView(){
        binding.apply {
            includeHeader.tvTitle.text = getString(R.string.register_school)
            includeHeader.tvInsert.text = getString(R.string.register_school_description)
        }
    }

    /** initObservers - focus in the variables from viewmodel
     * @author pelkidev
     * @since 1.0.0
     * `cctField` check the cct and  fill other fields
     * `numberPerdiod` check the number of periods selected
     * `datePeriod` check the  date and post the date in correct view
     * */
    private fun initObserver(){
        registerSchoolViewModel.schoolCctField.observe(viewLifecycleOwner) { state ->
            binding.apply {
                when (state) {
                    is SuccessState -> {
                        inputCct.successET()
                        etSchoolName.setText(state.result?.nombreescuela)
                        etShift.setText(state.result?.turno)
                        etType.setText(state.result?.tipocicloescolar)
                        showLogicSpinner(state.result)
                    }

                    is ErrorState -> {
                        //inputCct.errorET(state.result)
                        cleanAutoText()
                    }

                    else -> {
                        inputCct.errorET(ModelCodeError.ET_EMPTY)
                        cleanAutoText()
                    }
                }
            }
        }

        registerSchoolViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if(state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }
                else ->  animationHandler?.hideLoadingAnimation()
            }
        }

        registerSchoolViewModel.allField.observe(viewLifecycleOwner) { state ->
           if(!state){
           showCustomToastFailed(requireActivity(),getString(R.string.toast_error_validate_fields))
            }
        }
    }

    private fun showLogicSpinner(result: CctSchool?) {
        binding.apply {
            val grade = includeSpinnerGrade.spinner.fillItem(requireContext(), ModelSpinnerSelect.GRADE, result?.tipoescuela)
            val group = includeSpinnerGroup.spinner.fillItem(requireContext(), ModelSpinnerSelect.GROUP, result?.tipoescuela)
            val cycle = includeSpinnerCycle.spinner.fillItem(requireContext(), ModelSpinnerSelect.CYCLE, result?.tipocicloescolar)
            registerSchoolViewModel.saveGrade(grade)
            registerSchoolViewModel.saveGroup(group)
            registerSchoolViewModel.saveCycle(cycle)
        }
    }

    /** initListeners - Build the click on the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initListener(){
        binding.apply {
            includeHeader.btnReturn.setOnClickListener {
                findNavController().popBackStack()
            }

            includeButton.btnAction.setOnClickListener {
                registerSchoolViewModel.validateFields(
                    includeSpinnerGrade.spinner.toString(),
                    includeSpinnerGroup.spinner.toString(),
                    includeSpinnerCycle.spinner.toString()
                )
            }

            /** Spinner Section*/
            includeSpinnerGrade.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedValue = parent?.getItemAtPosition(position).toString()
                    registerSchoolViewModel.saveGrade(selectedValue)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //Nothing
                }
            }
            includeSpinnerGroup.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedValue = parent?.getItemAtPosition(position).toString()
                    registerSchoolViewModel.saveGroup(selectedValue)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //Nothing
                }
            }
            includeSpinnerCycle.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
    private fun initWatcher(){
        binding.apply {
            etCct.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
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
                    if(s.toString().length == 10) registerSchoolViewModel.getSchoolCCT(s.toString())
                }
            })
        }
    }

    /** cleanAutoText - In order to cct
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun cleanAutoText(){
        binding.apply {
            etSchoolName.setText(getString(R.string.tools_empty))
            etShift.setText(getString(R.string.tools_empty))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}