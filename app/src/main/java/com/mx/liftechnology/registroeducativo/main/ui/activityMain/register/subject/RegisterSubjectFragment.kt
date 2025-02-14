package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.subject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.domain.interfaces.AnimationHandler
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterSubjectBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterSubjectFragment : Fragment() {

    private var _binding: FragmentRegisterSubjectBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val registerSubjectViewModel: RegisterSubjectViewModel by viewModel()

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterSubjectBinding.inflate(inflater, container, false)
        return binding.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationHandler = context as? AnimationHandler
        initialView()
        initListener()
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
            includeHeader.tvTitle.text = getString(R.string.register_student_name)
            includeHeader.tvInsert.text = getString(R.string.register_student_name_description)
            includeHeader.btnReturn.visibility = View.VISIBLE
            includeButton.btnAction.text = getString(R.string.register_student_register)
        }
    }

    private fun initListener(){
        binding.includeHeader.btnReturn.setOnClickListener { findNavController().popBackStack() }
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
    }
}
