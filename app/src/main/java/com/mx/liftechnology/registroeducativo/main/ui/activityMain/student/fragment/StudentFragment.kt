package com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.fragment

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
import com.mx.liftechnology.data.model.StudentEntity
import com.mx.liftechnology.registroeducativo.databinding.FragmentStudentBinding
import com.mx.liftechnology.registroeducativo.main.adapters.StudentAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.StudentClickListener
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.student.StudentViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/** StudentFragment - Show all the students in a list, can add new, edit or delete
 * @author pelkidev
 * @since 1.0.0
 */
class StudentFragment : Fragment() {

    private var _binding: FragmentStudentBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val studentViewModel: StudentViewModel by sharedViewModel()

    /* Adapter variable */
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

    /** initListeners - Build the click on the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initListeners() {
        binding.btnAdd.setOnClickListener {
            val direction =
                StudentFragmentDirections.actionStudentFragmentToFormStudenFragment(null)
            findNavController().navigate(direction)
        }
    }

    /** initObservers - Read variable from viewmodel and do something
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initObservers() {
        studentViewModel.listStudents.observe(viewLifecycleOwner) { listStudents ->
            inflateAdapter(listStudents)
        }
    }

    /** inflateAdapter - Build the adapter of student
     * @author pelkidev
     * @since 1.0.0
     * @param items list the option from student
     * */
    private fun inflateAdapter(items: List<StudentEntity>) {
        val clickListener = StudentClickListener(
            onItemClick = { _ ->
                //Nothing
            },
            onMenuClick = { item, position ->
                performOptionsMenuClick(item, position)
            }
        )

        /* Build the adapter */
        adapterStudent = StudentAdapter(items, clickListener)
        binding.rvCardStudent.layoutManager = LinearLayoutManager(this.context)
        binding.apply {
            rvCardStudent.adapter = adapterStudent
        }
    }

    /** performOptionsMenuClick - Build the options card
     * @author pelkidev
     * @since 1.0.0
     * @param items list the option from student
     * @param position select the correct option
     * */
    private fun performOptionsMenuClick(
        items: StudentEntity,
        position: Int
    ) {
        /* Build the menu */
        val popupMenu = PopupMenu(
            requireActivity(),
            binding.rvCardStudent[position].findViewById(R.id.iv_image)
        )

        popupMenu.menu.add(Menu.NONE, 1, 1, "Editar")
        popupMenu.menu.add(Menu.NONE, 2, 2, "Eliminar")

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item?.itemId) {
                1 -> {
                    val direction =
                        StudentFragmentDirections.actionStudentFragmentToFormStudenFragment(items)
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