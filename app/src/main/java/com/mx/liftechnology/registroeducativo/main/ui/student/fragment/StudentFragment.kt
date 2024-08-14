package com.mx.liftechnology.registroeducativo.main.ui.student.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.data.local.entity.StudentEntity
import com.mx.liftechnology.registroeducativo.databinding.FragmentStudentBinding
import com.mx.liftechnology.registroeducativo.main.adapters.StudentAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.StudentClickListener
import com.mx.liftechnology.registroeducativo.main.ui.student.StudentViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StudentFragment : Fragment() {

    private var _binding: FragmentStudentBinding? = null
    private val binding get() = _binding!!
    private val studentViewModel: StudentViewModel by sharedViewModel()
    private var adapterStudent: StudentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        studentViewModel.getData()
        initListeners()
        initObservers()
        return binding.root
    }

    private fun initListeners(){
        binding.btnAdd.setOnClickListener {
            val direction = StudentFragmentDirections.actionStudentFragmentToFormStudenFragment(null)
            findNavController().navigate(direction)
        }
    }

    private fun initObservers(){
        studentViewModel.listStudents.observe(viewLifecycleOwner){ listStudents ->
            inflateAdapter(listStudents)
        }
    }

    private fun inflateAdapter(items: List<StudentEntity>){
        val clickListener = StudentClickListener(
            onItemClick = { item ->
                // Manejar el clic en la vista completa
            },
            onMenuClick = { item , position ->
                performOptionsMenuClick(item, position)
            }
        )

        adapterStudent = StudentAdapter(items, clickListener)
        binding.rvCardStudent.layoutManager = LinearLayoutManager(this.context)
        binding.apply {
            rvCardStudent.adapter = adapterStudent
        }

    }

    private fun performOptionsMenuClick(
        items: StudentEntity,
        position: Int
    ) {
        val popupMenu = PopupMenu(
            requireActivity(),
            binding?.rvCardStudent?.get(position)?.findViewById(R.id.iv_image)
        )

        popupMenu.menu.add(Menu.NONE, 1, 1, "Editar")
        popupMenu.menu.add(Menu.NONE, 2, 2, "Eliminar")


        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item?.itemId) {
                1 -> {
                    val direction = StudentFragmentDirections.actionStudentFragmentToFormStudenFragment(items)
                    findNavController().navigate(direction)
                    return@OnMenuItemClickListener true
                }
                2 -> {

                    return@OnMenuItemClickListener true
                }
            }
            false
        })

        popupMenu.show()
    }
}