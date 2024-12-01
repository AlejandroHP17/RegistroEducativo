package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

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
import com.mx.liftechnology.registroeducativo.databinding.FragmentMenuBinding
import com.mx.liftechnology.registroeducativo.main.adapters.MenuAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.MenuClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

/** MenuFragment - Show the different available option that the user has
 * @author pelkidev
 * @since 1.0.0
 */
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val menuViewModel: MenuViewModel by viewModel()

    /* Adapter variable */
    private var adapterMenu: MenuAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menuViewModel.getMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        initialView()
        initObservers()
        return binding.root
    }

    /** initialView - Print the correct view
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initialView() {
        binding.apply {
            tvGretting.text = getString(R.string.menu_grettins)
            tvName.text = getString(R.string.menu_empty)
        }
    }

    /** initObservers - Read variable from viewmodel and do something
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initObservers() {
        /* Show all the options from menu, or if any error occur, show the error */
        menuViewModel.nameMenu.observe(viewLifecycleOwner) { state ->
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
                ModelSelectorMenu.EVALUATION.value -> null
                ModelSelectorMenu.CONTROL.value -> MenuFragmentDirections.actionMenuFragmentToSubMenuFragment()
                ModelSelectorMenu.PROFILE.value -> null
                ModelSelectorMenu.CONFIGURATION.value -> null
                else -> null
            }
            direction?.let { findNavController().navigate(it) }
        }

        /* Build the adapter */
        adapterMenu = MenuAdapter(items, clickListener)
        binding.rvCardMenu.layoutManager = GridLayoutManager(this.context, 2)
        binding.apply {
            rvCardMenu.adapter = adapterMenu
            contentMenu.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}