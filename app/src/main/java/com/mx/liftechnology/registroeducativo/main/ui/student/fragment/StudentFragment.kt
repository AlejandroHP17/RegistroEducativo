package com.mx.liftechnology.registroeducativo.main.ui.student.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.registroeducativo.data.local.entity.StudentEntity
import com.mx.liftechnology.registroeducativo.databinding.FragmentStudentBinding
import com.mx.liftechnology.registroeducativo.main.adapters.MenuAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.MenuClickListener
import com.mx.liftechnology.registroeducativo.main.adapters.StudentAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.StudentClickListener
import com.mx.liftechnology.registroeducativo.main.ui.home.MenuFragmentDirections
import com.mx.liftechnology.registroeducativo.main.ui.student.StudentViewModel
import com.mx.liftechnology.registroeducativo.main.viewextensions.toastFragment
import com.mx.liftechnology.registroeducativo.model.util.ModelSelectorMenu
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StudentFragment : Fragment() {

    private var _binding: FragmentStudentBinding? = null
    private val binding get() = _binding!!
    private val studentViewModel: StudentViewModel by sharedViewModel()
    private var adapterStudent: StudentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studentViewModel.getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        initListeners()
        initObservers()
        return binding.root
    }

    private fun initListeners(){
        binding.btnAdd.setOnClickListener {
            val direction = StudentFragmentDirections.actionStudentFragmentToFormStudenFragment()
            findNavController().navigate(direction)
        }
    }

    private fun initObservers(){
        studentViewModel.listStudents.observe(viewLifecycleOwner){ listStudents ->
            inflateAdapter(listStudents)
        }
    }

    private fun inflateAdapter(items: List<StudentEntity>){
        val clickListener = StudentClickListener { item ->

            // Manejar el clic aquí
        }

        adapterStudent = StudentAdapter(items, clickListener)
        binding.rvCardStudent.layoutManager = LinearLayoutManager(this.context)
        binding.apply {
            rvCardStudent.adapter = adapterStudent
        }

    }
}