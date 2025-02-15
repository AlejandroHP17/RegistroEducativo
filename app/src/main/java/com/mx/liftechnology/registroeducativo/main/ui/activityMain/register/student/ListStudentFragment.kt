package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorStateUser
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.interfaces.AnimationHandler
import com.mx.liftechnology.domain.model.ModelStudent
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentEmptyStateBinding
import com.mx.liftechnology.registroeducativo.databinding.FragmentListStudentSubjectBinding
import com.mx.liftechnology.registroeducativo.main.adapters.StudentAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.StudentClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListStudentFragment : Fragment() {

    private var _binding: FragmentListStudentSubjectBinding? = null
    private val binding get() = _binding!!
    private var emptyStateBinding: FragmentEmptyStateBinding? = null

    private var studentAdapter : StudentAdapter? = null
    private var listStudent: MutableList<ModelStudent?>? = null
    private var inflatedView: View? = null

    /* View Model variable */
    private val listStudentViewModel: ListStudentViewModel by viewModel()

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listStudentViewModel.getListStudent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListStudentSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationHandler = context as? AnimationHandler
        animationHandler?.showLoadingAnimation()
        initView()
        initListener()
    }

    override fun onStart() {
        super.onStart()
        initObserver()
        listStudentViewModel.getLocalListStudent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animationHandler = null
        _binding = null
        emptyStateBinding = null
        studentAdapter = null
    }

    private fun initView(){
        binding.apply {
            includeHeader.tvTitle.text = getString(R.string.get_student_name)
            includeButton.btnAction.text = getString(R.string.add_button)
            includeButton.btnRecord.visibility = View.GONE
        }
    }

    /** initialView - Print the correct view, menu or empty state
     * @author pelkidev
     * @since 1.0.0
     */
    private fun emptyView() {
        binding.apply {
            if(inflatedView == null){
                inflatedView = binding.emptyStateStub.inflate()

                // Obtener el binding de la vista inflada
                emptyStateBinding = FragmentEmptyStateBinding.bind(inflatedView!!)

                emptyStateBinding?.apply {
                    includeButton.btnAction.setOnClickListener {
                        val nav =
                            ListStudentFragmentDirections.actionListStudentFragmentToRegisterStudentFragment()
                        findNavController().navigate(nav)
                    }
                    includeButton.btnAction.text = getString(R.string.add_button)
                    includeButton.btnRecord.visibility = View.GONE
                    tvEsTitle.text = getString(R.string.empty_student_1)
                    tvEsDescription.text = getString(R.string.empty_student_2)
                    ivEsImage.setImageResource(R.drawable.ic_empty_student)
                }
            }

            includeButton.llButtons.visibility = View.GONE
            includeHeader.tvTitle.visibility = View.GONE
            rvListStudent.visibility = View.GONE

            // Acceder al botón después de inflar
            emptyStateStub.visibility = View.VISIBLE
        }

    }

    private fun initListener(){
        binding.includeHeader.btnReturn.setOnClickListener { findNavController().popBackStack() }
        binding.includeButton.btnAction.setOnClickListener {
            val nav =  ListStudentFragmentDirections.actionListStudentFragmentToRegisterStudentFragment()
            findNavController().navigate(nav)
        }
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

        listStudentViewModel.responseListStudent.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    listStudent = state.result?.toMutableList()
                    initAdapterStudent()
                }
                is ErrorState -> emptyView()
                is ErrorStateUser ->  emptyView()
                else -> {
                    emptyView()
                }
            }
        }
    }

    private fun initAdapterStudent(){

        /* Build the adapter */
        studentAdapter = StudentAdapter(listStudent, StudentClickListener { item ->
            // Aquí manejas el click del estudiante``
            // Puedes navegar a otro fragment o ejecutar otra acción aquí
        })
        binding.apply {
            rvListStudent.layoutManager = LinearLayoutManager(context)
            rvListStudent.adapter = studentAdapter
        }
    }
}