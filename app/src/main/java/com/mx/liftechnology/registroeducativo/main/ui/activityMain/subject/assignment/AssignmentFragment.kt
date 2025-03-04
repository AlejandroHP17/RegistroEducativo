package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.assignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentAssignmentBinding
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

class AssignmentFragment : Fragment() {

    private var _binding: FragmentAssignmentBinding? = null
    private val binding get() = _binding!!

    private val assignmentViewModel: AssignmentViewModel by viewModel()

    private val args: AssignmentFragmentArgs by navArgs()

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAssignmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationHandler = context as? AnimationHandler
        animationHandler?.showLoadingAnimation()
        initView()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        assignmentViewModel.loaderState(false)
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
            includeHeaderAssignment.tvTitle.text = args.itemSubject.name
            includeHeaderAssignment.tvInsert.text = getString(R.string.assignment_description)
            includeButtonAssignment.btnAction.text = getString(R.string.add_button)
            includeButtonAssignment.btnRecord.visibility = View.GONE
        }
    }

    private fun initListeners(){
        binding.apply {
            includeButtonAssignment.btnAction.setOnClickListener {
                val nav = AssignmentFragmentDirections.actionAssignmentFragmentToRegisterAssignmentFragment(args.itemSubject)
                findNavController().navigate(nav)
            }
            includeHeaderAssignment.btnReturn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
    private fun initObservers() {
        assignmentViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if (state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }

                else -> animationHandler?.hideLoadingAnimation()
            }
        }
    }
}