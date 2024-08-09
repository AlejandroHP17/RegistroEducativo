package com.mx.liftechnology.registroeducativo.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mx.liftechnology.registroeducativo.databinding.FragmentMenuBinding
import com.mx.liftechnology.registroeducativo.main.adapters.MenuAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.MenuClickListener
import com.mx.liftechnology.registroeducativo.main.dialogs.CustomAddDialog
import com.mx.liftechnology.registroeducativo.main.viewextensions.toastFragment
import com.mx.liftechnology.registroeducativo.model.util.EmptyState
import com.mx.liftechnology.registroeducativo.model.util.ErrorState
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelAdapterMenu
import com.mx.liftechnology.registroeducativo.model.util.ModelSelectorDialog
import com.mx.liftechnology.registroeducativo.model.util.ModelSelectorMenu
import com.mx.liftechnology.registroeducativo.model.util.SuccessState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val menuViewModel: MenuViewModel by sharedViewModel()
    private var adapterMenu: MenuAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        initialView(false)
        initObservers()
        initListeners()
        return binding.root
    }

    private fun initialView(flag : Boolean){
        binding.apply {
            if(flag){
                menuViewModel.getMenu()
            }else{
                includeEmptyHome.viewEmptyHome.visibility = View.VISIBLE
                contentMenu.visibility = View.GONE
            }
        }
    }
    private fun initObservers() {
        menuViewModel.nameCourse.observe(viewLifecycleOwner){ text ->
            if(!text.isNullOrEmpty()){
                binding.tvTitleCard.text = text
                initialView(true)
            }
        }
        menuViewModel.nameMenu.observe(viewLifecycleOwner){ state->
            when(state){
                is SuccessState  -> { inflateAdapter(state.result) }
                is ErrorState  -> { toastFragment("Error code: ${state.result}")  }
                is EmptyState  -> { toastFragment("Por el momento no podemos mostrar el menu")}
            }
        }
    }
    private fun initListeners(){
        binding.includeEmptyHome.btnAdd.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialogFragment = CustomAddDialog.newInstance( ModelSelectorDialog.ADD)

        childFragmentManager.let {
            dialogFragment.show(it, "customDialog")
        }
    }
    private fun inflateAdapter(items: List<ModelAdapterMenu>){
        val clickListener = MenuClickListener { item ->
            when(item.id){
                ModelSelectorMenu.CALENDAR.value -> {}
                ModelSelectorMenu.STUDENT.value -> {
                    val direction = MenuFragmentDirections.actionMenuFragmentToStudentFragment()
                    findNavController().navigate(direction)
                }
                ModelSelectorMenu.SCHOOL.value -> {}
                ModelSelectorMenu.EXPORT.value -> {}
                ModelSelectorMenu.PERIOD.value -> {}
                ModelSelectorMenu.CONFIG.value -> {}
            }
            // Manejar el clic aquí
            toastFragment("Clicked on: ${item.titleCard}")
        }

        adapterMenu = MenuAdapter(items, clickListener)
        binding.rvCardMenu.layoutManager = GridLayoutManager(this.context,2)
        binding.apply {
            rvCardMenu.adapter = adapterMenu
            includeEmptyHome.viewEmptyHome.visibility = View.GONE
            contentMenu.visibility = View.VISIBLE
        }

    }
}