package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.registerassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.student.ModelStudentRegisterAssignmentDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterAssignmentBinding
import com.mx.liftechnology.registroeducativo.main.adapters.StudentAssignmentAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.StudentAssignmentClickListener
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterAssignmentFragment : Fragment() {

    private var _binding: FragmentRegisterAssignmentBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val registerAssignmentViewModel : RegisterAssignmentViewModel by viewModel()

    private val args: RegisterAssignmentFragmentArgs by navArgs()
    private var listStudent: MutableList<ModelStudentRegisterAssignmentDomain>? = null
    private var studentAssignmentAdapter: StudentAssignmentAdapter? = null

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterAssignmentBinding.inflate(inflater, container, false)
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
        registerAssignmentViewModel.getListStudent()
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
            includeHeaderRegisterAssignment.tvTitle.text = args.itemRegisterAssignment.name
            includeHeaderRegisterAssignment.tvInsert.visibility = View.GONE
            includeButtonRegisterAssignment.btnAction.text = getString(R.string.add_button)
            includeButtonRegisterAssignment.btnRecord.visibility = View.GONE
        }
    }

    private fun initListeners(){
        binding.apply {
            includeHeaderRegisterAssignment.btnReturn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initObservers() {
        registerAssignmentViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if (state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }

                else -> animationHandler?.hideLoadingAnimation()
            }
        }

        registerAssignmentViewModel.responseListStudent.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    listStudent = state.result?.toMutableList()
                    initAdapterStudentAssignment()

                    registerAssignmentViewModel.loaderState(false)
                }
                else -> {

                    registerAssignmentViewModel.loaderState(false)
                }
            }
        }
    }

    private fun initAdapterStudentAssignment(){
        studentAssignmentAdapter = StudentAssignmentAdapter(StudentAssignmentClickListener (
            onItemClick = {item ->

            }
        )
        )

        listStudent?.let { studentAssignmentAdapter!!.updateList(it.toList()) }
        binding.apply {
            rvListStudentAssignment.layoutManager = LinearLayoutManager(context)
            rvListStudentAssignment.adapter = studentAssignmentAdapter
        }
    }

}