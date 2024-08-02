package com.mx.liftechnology.registroeducativo.ui.home.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentMenuBinding
import com.mx.liftechnology.registroeducativo.ui.home.viewmodel.MenuViewModel

class MenuFragment : Fragment() {

    private var flag = false

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val menuViewModel: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        initialView()
        initListeners()
        return binding.root
    }

    private fun initialView(){
        binding.includeEmptyHome.apply {
            if(flag){
                this.viewEmptyHome.visibility = View.GONE
            }else{
                this.viewEmptyHome.visibility = View.VISIBLE
            }
        }
    }

    private fun initListeners(){
        binding.includeEmptyHome.btnAdd.setOnClickListener {

        }
    }
}