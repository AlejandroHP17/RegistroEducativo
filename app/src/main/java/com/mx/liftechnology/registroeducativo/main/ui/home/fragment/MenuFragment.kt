package com.mx.liftechnology.registroeducativo.main.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mx.liftechnology.registroeducativo.databinding.FragmentMenuBinding
import com.mx.liftechnology.registroeducativo.main.dialogs.CustomAddDialog
import com.mx.liftechnology.registroeducativo.main.ui.home.viewmodel.MenuViewModel
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelSelectorDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val menuViewModel: MenuViewModel by sharedViewModel()

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

    private fun initObservers() {
        menuViewModel.nameCourse.observe(viewLifecycleOwner){ text ->
            if(!text.isNullOrEmpty()){
                binding.tvTitleCard.text = text
                initialView(true)
            }
        }
    }

    private fun initialView(flag : Boolean){
        binding.apply {
            if(flag){
                includeEmptyHome.viewEmptyHome.visibility = View.GONE
                contentMenu.visibility = View.VISIBLE
            }else{
                includeEmptyHome.viewEmptyHome.visibility = View.VISIBLE
                contentMenu.visibility = View.GONE
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
}