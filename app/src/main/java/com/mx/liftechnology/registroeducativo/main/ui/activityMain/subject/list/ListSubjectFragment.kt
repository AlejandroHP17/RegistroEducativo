package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentEmptyStateBinding
import com.mx.liftechnology.registroeducativo.databinding.FragmentListStudentSubjectBinding
import com.mx.liftechnology.registroeducativo.main.adapters.SubjectAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.SubjectClickListener
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import com.mx.liftechnology.registroeducativo.main.viewextensions.showPopUpMore
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListSubjectFragment : Fragment() {

    private var _binding: FragmentListStudentSubjectBinding? = null
    private val binding get() = _binding!!
    private var emptyStateBinding: FragmentEmptyStateBinding? = null

    private lateinit var subjectAdapter: SubjectAdapter
    private var inflatedView: View? = null

    /* View Model variable */
    private val listSubjectViewModel: ListSubjectViewModel by viewModel()

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listSubjectViewModel.getSubject()
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
        initSubjectAdapter()
        initView()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        initObservers()
        listSubjectViewModel.getLocalListSubject()
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
    private fun initView() {
        binding.apply {
            includeHeader.tvTitle.text = getString(R.string.get_subject_name)
            includeButton.btnAction.text = getString(R.string.add_button)
            includeButton.btnRecord.visibility = View.GONE
        }
    }

    private fun emptyView() {
        binding.apply {

            if (inflatedView == null) {
                inflatedView = binding.emptyStateStub.inflate()

                // Obtener el binding de la vista inflada
                emptyStateBinding = FragmentEmptyStateBinding.bind(inflatedView!!)

                emptyStateBinding?.apply {
                    includeButton.btnAction.setOnClickListener {
                        val nav =
                            ListSubjectFragmentDirections.actionListSubjectFragmentToRegisterSubjectFragment()
                        findNavController().navigate(nav)
                    }
                    includeButton.btnAction.text = getString(R.string.add_button)
                    includeButton.btnRecord.visibility = View.GONE
                    tvEsTitle.text = getString(R.string.empty_subject_1)
                    tvEsDescription.text = getString(R.string.empty_subject_2)
                    ivEsImage.setImageResource(R.drawable.ic_empty_subject)
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
                ListSubjectFragmentDirections.actionListSubjectFragmentToRegisterSubjectFragment()
            findNavController().navigate(nav)
        }
    }

    private fun initObservers() {
        listSubjectViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if (state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }

                else -> animationHandler?.hideLoadingAnimation()
            }
        }

        listSubjectViewModel.responseListSubject.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> subjectAdapter.updateList(state.result ?: emptyList())
                else -> emptyView()
            }
        }
    }

    private fun initSubjectAdapter() {
        /* Build the adapter */
        subjectAdapter = SubjectAdapter(
            SubjectClickListener(
                onItemClick = { item ->
                    val nav = ListSubjectFragmentDirections.actionListSubjectFragmentToAssignmentFragment(item)
                    findNavController().navigate(nav)
                },
                onItemMore = { view, item ->
                    this.showPopUpMore(
                        view,
                        item,
                        onDelete = { subjectToDelete -> deleteSubject(subjectToDelete) },
                        onEditNavigate = {
                            val nav = ListSubjectFragmentDirections.actionListSubjectFragmentToRegisterSubjectFragment()
                            findNavController().navigate(nav)
                        }
                    )
                }
            )
        )

        binding.rvListStudent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = subjectAdapter
        }
    }
    private fun deleteSubject(subject: ModelFormatSubjectDomain) {
        // Lógica para eliminar estudiante
    }
}