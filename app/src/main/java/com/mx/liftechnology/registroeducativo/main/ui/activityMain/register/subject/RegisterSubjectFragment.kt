package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.subject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.domain.interfaces.AnimationHandler
import com.mx.liftechnology.domain.model.ModelFormatSubject
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterSubjectBinding
import com.mx.liftechnology.registroeducativo.main.adapters.FormatSubjectAdapter
import com.mx.liftechnology.registroeducativo.main.util.ModelSpinnerSelect
import com.mx.liftechnology.registroeducativo.main.viewextensions.fillItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterSubjectFragment : Fragment() {

    private var _binding: FragmentRegisterSubjectBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val registerSubjectViewModel: RegisterSubjectViewModel by viewModel()
    private var adapterSubject : FormatSubjectAdapter? = null

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterSubjectBinding.inflate(inflater, container, false)
        return binding.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationHandler = context as? AnimationHandler
        initialView()
        initListener()
        showLogicSpinner()
    }

    override fun onStart() {
        super.onStart()
        initObserver()
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
    private fun initialView() {
        binding.apply {
            includeHeader.tvTitle.text = getString(R.string.register_subject_name)
            includeHeader.tvInsert.text = getString(R.string.register_subject_name_description)
            includeHeader.btnReturn.visibility = View.VISIBLE
            includeButton.btnAction.text = getString(R.string.register_student_register)
        }
    }

    private fun initListener(){
        binding.apply {
            includeHeader.btnReturn.setOnClickListener { findNavController().popBackStack() }
            /** Spinner Section*/
            includeSpinnerSubject.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedValue = parent?.getItemAtPosition(position).toString()
                    registerSubjectViewModel.saveSubject(selectedValue)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //Nothing
                }
            }

            includeButton.btnAction.setOnClickListener {
                val updatedList = adapterSubject?.getList()
                registerSubjectViewModel.validateFields(updatedList, etField.text.toString())
            }
        }
    }

    private fun initObserver(){
        registerSubjectViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if(state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }
                else ->  animationHandler?.hideLoadingAnimation()
            }
        }

        registerSubjectViewModel.subjectNumber.observe(viewLifecycleOwner) { period ->
            initAdapterSubject(period)
        }
    }

    private fun showLogicSpinner(){
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
    private fun initAdapterSubject(period:Int){
        val list = MutableList(period) { index -> ModelFormatSubject(
            position = index,
            name = getString(R.string.register_subject_evaluation),
            percent = "0%"
        ) }

        /* Build the adapter */
        adapterSubject = FormatSubjectAdapter(list)
/*, SubjectClickListener{ item ->
            /* Open the calendar */
            // registerPartialViewModel.initDatePicker(item, parentFragmentManager, context)
        })*/
        binding.apply {
            rvCardSubject.layoutManager = LinearLayoutManager(context)
            rvCardSubject.adapter = adapterSubject
        }
    }
}
