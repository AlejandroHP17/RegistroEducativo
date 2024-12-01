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
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterSchoolBinding
import com.mx.liftechnology.registroeducativo.main.util.ModelSpinnerSelect
import com.mx.liftechnology.registroeducativo.main.viewextensions.errorET
import com.mx.liftechnology.registroeducativo.main.viewextensions.fillItem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Bring the cct available
        registerSchoolViewModel.getCCT()
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
            includeSpinnerType.spinner.fillItem(requireContext(), ModelSpinnerSelect.TYPE)
            includeSpinnerCycle.spinner.fillItem(requireContext(), ModelSpinnerSelect.CYCLE)
            includeSpinnerGrade.spinner.fillItem(requireContext(), ModelSpinnerSelect.GRADE)
            includeSpinnerGroup.spinner.fillItem(requireContext(), ModelSpinnerSelect.GROUP)
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
        registerSchoolViewModel.cctField.observe(viewLifecycleOwner) { state ->
            binding.apply {
                when (state) {
                    is SuccessState -> {
                        inputCct.successET()
                        etSchoolName.setText("Amado Nervo")
                        etShift.setText("Matutino")
                        etSchoolTerm.setText("Ciclo escolar 2024 - 2025")
                    }

                    is ErrorState -> {
                        inputCct.errorET(state.result)
                        cleanAutoText()
                    }

                    else -> {
                        inputCct.errorET(ModelCodeError.ET_EMPTY)
                        cleanAutoText()
                    }
                }
            }
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
                    etShift.text.toString()
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
        }
    }

    /** initWatcher - Observe the cct field to validate if exist someone
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initWatcher(){
        binding.etCct.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                registerSchoolViewModel.validateCCT(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {
                //Nothing
            }
        })
    }

    /** cleanAutoText - In order to cct
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun cleanAutoText(){
        binding.apply {
            etSchoolName.setText(getString(R.string.tools_empty))
            etShift.setText(getString(R.string.tools_empty))
            etSchoolTerm.setText(getString(R.string.tools_empty))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}