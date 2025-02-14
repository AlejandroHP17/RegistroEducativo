package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.domain.interfaces.AnimationHandler
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentEmptyStateBinding
import com.mx.liftechnology.registroeducativo.databinding.FragmentListStudentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListStudentFragment : Fragment() {

    private var _binding: FragmentListStudentBinding? = null
    private val binding get() = _binding!!
    private var emptyStateBinding: FragmentEmptyStateBinding? = null

    /* View Model variable */
    private val listStudentViewModel: ListStudentViewModel by viewModel()

    /* loader variable */
    private var animationHandler: AnimationHandler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listStudentViewModel.getStudents()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationHandler = context as? AnimationHandler
        animationHandler?.showLoadingAnimation()
        initialView()
        initListener()
    }

    override fun onStart() {
        super.onStart()
        initObserver()

        listStudentViewModel.hideLoader()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animationHandler = null
        _binding = null
        emptyStateBinding = null
    }

    /** initialView - Print the correct view, menu or empty state
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initialView() {
        binding.apply {
            val inflatedView = binding.emptyStateStub.inflate()

            // Obtener el binding de la vista inflada
            emptyStateBinding = FragmentEmptyStateBinding.bind(inflatedView)

            emptyStateBinding?.apply {
                includeButton.btnAction.setOnClickListener {
                    val nav =  ListStudentFragmentDirections.actionListStudentFragmentToRegisterStudentFragment()
                    findNavController().navigate(nav)
                }
                includeButton.btnAction.text = getString(R.string.add_button)
                includeButton.btnRecord.visibility = View.GONE
                tvEsTitle.text = getString(R.string.empty_student_1)
                tvEsDescription.text = getString(R.string.empty_student_2)
                ivEsImage.setImageResource(R.drawable.ic_empty_student)
            }
            // Acceder al botón después de inflar
            emptyStateStub.visibility = View.VISIBLE
        }

    }

    private fun initListener(){
        binding.includeHeader.btnReturn.setOnClickListener { findNavController().popBackStack() }
    }

    private fun initObserver(){
        listStudentViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
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