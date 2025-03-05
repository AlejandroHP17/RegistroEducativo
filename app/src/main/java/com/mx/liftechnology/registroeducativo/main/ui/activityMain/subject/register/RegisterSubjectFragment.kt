package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterSubjectBinding
import com.mx.liftechnology.registroeducativo.main.adapters.FormatSubjectAdapter
import com.mx.liftechnology.registroeducativo.main.funextensions.log
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import com.mx.liftechnology.registroeducativo.main.util.ModelSpinnerSelect
import com.mx.liftechnology.registroeducativo.main.viewextensions.errorET
import com.mx.liftechnology.registroeducativo.main.viewextensions.fillItem
import com.mx.liftechnology.registroeducativo.main.viewextensions.showCustomToastFailed
import com.mx.liftechnology.registroeducativo.main.viewextensions.showCustomToastSuccess
import com.mx.liftechnology.registroeducativo.main.viewextensions.successET
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterSubjectFragment : Fragment() {

    private var _binding: FragmentRegisterSubjectBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val registerSubjectViewModel: RegisterSubjectViewModel by viewModel()
    private var subjectAdapter: FormatSubjectAdapter? = null

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerSubjectViewModel.getListEvaluationType()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationHandler = context as? AnimationHandler
        initView()
        initListeners()
        showLogicSpinner()
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
            includeHeader.tvTitle.text = getString(R.string.register_subject_name)
            includeHeader.tvInsert.text = getString(R.string.register_subject_name_description)
            includeHeader.btnReturn.visibility = View.VISIBLE
            includeButton.btnRecord.visibility = View.GONE
            includeButton.btnAction.text = getString(R.string.register_student_register)
        }
    }

    private fun initListeners() {
        binding.apply {
            includeHeader.btnReturn.setOnClickListener { findNavController().popBackStack() }
            /** Spinner Section*/
            includeSpinnerSubject.spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedValue = parent?.getItemAtPosition(position).toString()
                        registerSubjectViewModel.saveSubject(selectedValue)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        //Nothing
                    }
                }

            includeButton.btnAction.setOnClickListener {
                val updatedList = subjectAdapter?.getList()
                registerSubjectViewModel.validateFields(updatedList, etField.text.toString())
            }
        }
    }

    private fun initObservers() {
        registerSubjectViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if (state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }

                else -> animationHandler?.hideLoadingAnimation()
            }
        }

        registerSubjectViewModel.nameField.observe(viewLifecycleOwner){state ->
            when (state) {
                is SuccessState -> binding.inputField.successET()
                is ErrorState -> binding.inputField.errorET(state.result)
                else -> {binding.inputField.errorET(ModelCodeInputs.ET_MISTAKE_FORMAT)}
            }
        }

        registerSubjectViewModel.adapterField.observe(viewLifecycleOwner){state ->
            when (state) {
                is ErrorUserState ->{
                    registerSubjectViewModel.loaderState(false)
                    showCustomToastFailed(requireActivity(), state.result)
                }

                else -> {
                    registerSubjectViewModel.loaderState(false)
                    log(state.toString())
                }
            }
        }

        registerSubjectViewModel.subjectNumber.observe(viewLifecycleOwner) { period ->
            initAdapterSubject(period)
        }

        registerSubjectViewModel.responseSubjectRegister.observe(viewLifecycleOwner) { state ->
            log(state.toString())
            when (state) {
                is SuccessState ->{
                    showCustomToastSuccess(requireActivity(), state.toString())
                    findNavController().popBackStack()
                }

                is ErrorUserState -> showCustomToastFailed(requireActivity(), state.toString())

                else -> {

                }
            }
        }
    }

    private fun showLogicSpinner() {
        binding.apply {
            includeSpinnerSubject.spinner.fillItem(
                requireContext(),
                ModelSpinnerSelect.SUBJECT,
                null
            )
            includeSpinnerSubject.tvDemostration.visibility = View.GONE
            includeSpinnerSubject.spinner.visibility = View.VISIBLE
        }
    }

    /** initAdapterPeriod - Adapter specialized in the number of periods of user can select
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initAdapterSubject(period: Int) {
        val list = MutableList(period) { index ->
            ModelFormatSubjectDomain(
                position = index,
                name = "",
                percent = ""
            )
        }

        /* Build the adapter */
        subjectAdapter = FormatSubjectAdapter()
        /*, SubjectClickListener{ item ->
                    /* Open the calendar */
                    // registerPartialViewModel.initDatePicker(item, parentFragmentManager, context)
                })*/
        list.let { subjectAdapter!!.updateList(it.toList()) }
        binding.apply {
            rvCardSubject.layoutManager = LinearLayoutManager(context)
            rvCardSubject.adapter = subjectAdapter
        }
    }
}
