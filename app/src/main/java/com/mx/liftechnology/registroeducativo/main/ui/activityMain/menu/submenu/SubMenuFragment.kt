package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.submenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mx.liftechnology.core.model.ModelAdapterMenu
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.data.model.ModelSelectorMenu
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentSubMenuBinding
import com.mx.liftechnology.registroeducativo.main.adapters.MenuAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.MenuClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

/** MenuFragment - Show the different available option that the user has
 * @author pelkidev
 * @since 1.0.0
 */
class SubMenuFragment : Fragment() {

    private var _binding: FragmentSubMenuBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val subMenuViewModel: SubMenuViewModel by viewModel()

    /* Adapter variable */
    private var adapterSubMenu: MenuAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subMenuViewModel.getSubMenu(false)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubMenuBinding.inflate(inflater, container, false)

        initialView()
        initListener()
        initObservers()
        return binding.root
    }

    private fun initListener(){
        binding.apply {
            includeHeader.btnReturn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


    /** initialView - Print the correct view, menu or empty state
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initialView() {
        binding.includeHeader.apply {
            tvTitle.text = getString(R.string.sub_menu_title)
        }
    }

    /** initObservers - Read variable from viewmodel and do something
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initObservers() {
        /* Show all the options from menu, or if any error occur, show the error */
        subMenuViewModel.nameSubMenu.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> inflateAdapter(state.result)
                else -> {
                    //Nothing
                }
            }
        }
    }

    /** inflateAdapter - Build the adapter of menu
     * @author pelkidev
     * @since 1.0.0
     * @param items list the option from menu
     * */
    private fun inflateAdapter(items: List<ModelAdapterMenu>) {
        val clickListener = MenuClickListener { item ->
            val direction: NavDirections? = when (item.id) {
                ModelSelectorMenu.SCHOOL.value -> SubMenuFragmentDirections.actionSubMenuFragmentToRegisterSchoolFragment()
                ModelSelectorMenu.STUDENTS.value -> null
                ModelSelectorMenu.PARTIALS.value -> SubMenuFragmentDirections.actionSubMenuFragmentToRegisterPartialFragment()
                ModelSelectorMenu.SUBJECTS.value -> null
                else -> null
            }
            direction?.let { findNavController().navigate(it) }
        }

        /* Build the adapter */
        adapterSubMenu = MenuAdapter(items, clickListener)
        binding.rvCardMenu.layoutManager = GridLayoutManager(this.context, 2)
        binding.apply {
            rvCardMenu.adapter = adapterSubMenu
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}