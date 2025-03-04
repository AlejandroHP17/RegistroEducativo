package com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentEmptyStateBinding
import com.mx.liftechnology.registroeducativo.databinding.FragmentListStudentSubjectBinding
import com.mx.liftechnology.registroeducativo.main.adapters.StudentAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.StudentClickListener
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import com.mx.liftechnology.registroeducativo.main.viewextensions.showPopUpMore
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListStudentFragment : Fragment() {

    private var _binding: FragmentListStudentSubjectBinding? = null
    private val binding get() = _binding!!
    private var emptyStateBinding: FragmentEmptyStateBinding? = null

    private lateinit var studentAdapter: StudentAdapter
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
        initAdapterStudent()
        initView()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        initObservers()
        listStudentViewModel.getLocalListStudent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animationHandler = null
        _binding = null
        emptyStateBinding = null
    }

    private fun initView() {
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
            if (inflatedView == null) {
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

    private fun initListeners() {
        binding.includeHeader.btnReturn.setOnClickListener { findNavController().popBackStack() }
        binding.includeButton.btnAction.setOnClickListener {
            val nav =
                ListStudentFragmentDirections.actionListStudentFragmentToRegisterStudentFragment()
            findNavController().navigate(nav)
        }
    }

    private fun initObservers() {
        listStudentViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if (state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }

                else -> animationHandler?.hideLoadingAnimation()
            }
        }

        listStudentViewModel.responseListStudent.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> studentAdapter.updateList(state.result?: emptyList())
                else -> emptyView()
            }
        }
    }

    private fun initAdapterStudent() {

        /* Build the adapter */
        studentAdapter = StudentAdapter(StudentClickListener (
            onItemClick = { item ->
                val navigate = ListStudentFragmentDirections.actionListStudentFragmentToEditStudentFragment(item)
                findNavController().navigate(navigate)
            },
            onItemMore = { view,  item ->
                this.showPopUpMore(
                    view,
                    item,
                    onDelete = { studentToDelete -> deleteStudent(studentToDelete) },
                    onEditNavigate = {
                        val nav = ListStudentFragmentDirections.actionListStudentFragmentToEditStudentFragment(item)
                        findNavController().navigate(nav)
                    }
                )
            }
        ))

        binding.apply {
            rvListStudent.layoutManager = LinearLayoutManager(context)
            rvListStudent.adapter = studentAdapter
        }
    }

    private fun deleteStudent(student: ModelStudentDomain) {
        // Lógica para eliminar estudiante
    }


}