package com.mx.liftechnology.registroeducativo.main.ui.activityMain.evaluation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.domain.model.ModelStudent
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentCalendarBinding
import com.mx.liftechnology.registroeducativo.main.adapters.StudentAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.StudentClickListener
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.student.ListStudentFragmentDirections
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.student.ListStudentViewModel
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

/** CalendarFragment - Show the different available option that the user has
 * @author pelkidev
 * @since 1.0.0
 */
class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private var studentAdapter: StudentAdapter? = null
    private var listStudent: MutableList<ModelStudent?>? = null

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
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationHandler = context as? AnimationHandler
        initView()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animationHandler = null
        _binding = null
    }

    /** initialView - Print the correct view
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initView() {
        binding.apply {
            includeHeaderCalendar.tvTitle.text = getString(R.string.calendar)
            includeHeaderCalendar.tvInsert.visibility = View.GONE
        }
    }

    private fun initListeners() {
        binding.includeHeaderCalendar.btnReturn.setOnClickListener {
            findNavController().popBackStack()
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
                is SuccessState -> {
                    listStudent = state.result?.toMutableList()
                    initAdapterStudent()
                }
                else -> {

                }
            }
        }


    }

    private fun initAdapterStudent() {

        /* Build the adapter */
        studentAdapter = StudentAdapter(listStudent, StudentClickListener { item ->
            val navigate = ListStudentFragmentDirections.actionListStudentFragmentToEditStudentFragment(item)
            findNavController().navigate(navigate)
        })
        binding.apply {
            rvListCalendar.layoutManager = LinearLayoutManager(context)
            rvListCalendar.adapter = studentAdapter
        }
    }


}