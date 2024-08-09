package com.mx.liftechnology.registroeducativo.main.ui.student

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentMenuBinding
import com.mx.liftechnology.registroeducativo.databinding.FragmentStudentBinding
import com.mx.liftechnology.registroeducativo.main.adapters.MenuAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StudentFragment : Fragment() {

    private var _binding: FragmentStudentBinding? = null
    private val binding get() = _binding!!
    private val studentViewModel: StudentViewModel by sharedViewModel()
    //private var adapterStudent: MenuAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        initListeners()
        return binding.root
    }

    private fun initListeners(){
        binding.btnAdd.setOnClickListener {
            val direction = StudentFragmentDirections.actionStudentFragmentToFormStudenFragment()
            findNavController().navigate(direction)
        }
    }
}