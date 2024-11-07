package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mx.liftechnology.core.model.modelBase.EmptyState
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.util.ModelSelectorDialog
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentMenuBinding
import com.mx.liftechnology.registroeducativo.main.adapters.MenuAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.MenuClickListener
import com.mx.liftechnology.registroeducativo.main.dialogs.CustomAddDialog
import com.mx.liftechnology.registroeducativo.main.util.ModelSelectorMenu
import com.mx.liftechnology.registroeducativo.main.viewextensions.toastFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/** MenuFragment - Show the different available option that the user has
 * @author pelkidev
 * @since 1.0.0
 */
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val menuViewModel: MenuViewModel by sharedViewModel()

    /* Adapter variable */
    private var adapterMenu: MenuAdapter? = null

    /* Auxiliar variable*/
    private var valueInitial: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        initialView()
        initObservers()
        initListeners()
        return binding.root
    }

    /** initData - Get the data in order to print the correct view (home)
     * @author pelkidev
     * @since 1.0.0
     * @param value help to know the first view, a menu or empty state
     */


    /** initialView - Print the correct view, menu or empty state
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initialView() {
        binding.apply {
            menuViewModel.getMenu()
            tvGretting.text = getString(R.string.menu_grettins)
            tvName.text = getString(R.string.menu_empty)
        }
    }

    /** initObservers - Read variable from viewmodel and do something
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initObservers() {
        /* If nameCourse has value, save in preference and prit the correct view */
        menuViewModel.nameCourse.observe(viewLifecycleOwner) { text ->
            if (!text.isNullOrEmpty()) {

                initialView()
            }
        }
        /* Show all the options from menu, or if any error occur, show the error */
        menuViewModel.nameMenu.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    inflateAdapter(state.result)
                }

                is ErrorState -> {
                    toastFragment("Error code: ${state.result}")
                }

                is EmptyState -> {
                    toastFragment("Por el momento no podemos mostrar el menu")
                }
            }
        }
    }

    /** initListeners - Build the click on the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initListeners() {
        binding.includeEmptyHome.btnAdd.setOnClickListener {
            showDialog()
        }
    }

    /** showDialog - Build the dialog to add
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun showDialog() {
        val dialogFragment = CustomAddDialog.newInstance(ModelSelectorDialog.ADD)
        childFragmentManager.let {
            dialogFragment.show(it, "customDialog")
        }
    }

    /** inflateAdapter - Build the adapter of menu
     * @author pelkidev
     * @since 1.0.0
     * @param items list the option from menu
     * */
    private fun inflateAdapter(items: List<com.mx.liftechnology.core.model.ModelAdapterMenu>) {
        val clickListener = MenuClickListener { item ->
            val direction: NavDirections? = when (item.id) {
                ModelSelectorMenu.EVALUATION.value -> {
                    null
                }

                ModelSelectorMenu.CONTROL.value -> {
                    MenuFragmentDirections.actionMenuFragmentToSubMenuFragment()
                }

                ModelSelectorMenu.PROFILE.value -> {
                    null
                }

                ModelSelectorMenu.CONFIGURATION.value -> {
                    null
                }

                else -> {
                    null
                }
            }
            if (direction != null) {
                findNavController().navigate(direction)
            }

            toastFragment("Clicked on: ${item.titleCard}")
        }

        /* Build the adapter */
        adapterMenu = MenuAdapter(items, clickListener)
        binding.rvCardMenu.layoutManager = GridLayoutManager(this.context, 2)
        binding.apply {
            rvCardMenu.adapter = adapterMenu
            includeEmptyHome.viewEmptyHome.visibility = View.GONE
            contentMenu.visibility = View.VISIBLE
        }
    }
}