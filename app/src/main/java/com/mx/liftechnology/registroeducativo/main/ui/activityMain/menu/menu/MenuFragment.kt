package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mx.liftechnology.core.model.ModelDialogStudentGroup
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorStateUser
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.data.model.ModelAdapterMenu
import com.mx.liftechnology.data.model.ModelSelectorMenu
import com.mx.liftechnology.domain.interfaces.AnimationHandler
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentMenuBinding
import com.mx.liftechnology.registroeducativo.main.adapters.MenuAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.MenuClickListener
import com.mx.liftechnology.registroeducativo.main.funextensions.log
import com.mx.liftechnology.registroeducativo.main.util.DialogSelectGroup
import com.mx.liftechnology.registroeducativo.main.viewextensions.showCustomToastFailed
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

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationHandler = context as? AnimationHandler
        initView()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        menuViewModel.getGroup()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animationHandler = null
        _binding = null
    }

    /** initialView - Print the correct view
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initView() {
        binding.apply {
            tvGretting.text = getString(R.string.menu_grettins, "Profesor")
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

        /* Show all the options from menu, or if any error occur, show the error */
        menuViewModel.listGroup.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    menuViewModel.getMenu(true)
                    binding.tvName.text = state.result.infoShowSchool
                }
                is ErrorState -> {
                    menuViewModel.getMenu(false)
                    log(state.result)
                }
                is ErrorStateUser -> {
                    menuViewModel.getMenu(false)
                    showCustomToastFailed(requireActivity(), state.result)
                }
                else -> {
                    //Nothing
                }
            }
        }

        menuViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if (state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }
                else -> animationHandler?.hideLoadingAnimation()
            }
        }
    }

    private fun initListeners() {
        binding.cvNameCourse.setOnClickListener {
            showDialog()
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
                ModelSelectorMenu.PROFILE.value -> MenuFragmentDirections.actionMenuFragmentToProfileFragment()
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

    private fun showDialog() {
        val result = menuViewModel.listGroup.value

        if (result is SuccessState) {  // Aseguramos que el estado es SuccessState
            result.result.listSchool?.let { listSchool ->
                // Convertimos la lista de ResponseGroupTeacher a ModelDialogStudentGroup
                val modelDialogStudentGroups: List<ModelDialogStudentGroup> =
                    listSchool.map { teacher ->
                        ModelDialogStudentGroup(
                            selected = false,  // Inicializamos 'selected' como false
                            item = teacher,    // Asignamos el objeto ResponseGroupTeacher
                            nameItem = result.result.infoShowSchool
                        )
                    }

                val dialogManager =
                    DialogSelectGroup(requireContext(), modelDialogStudentGroups) { selectedItem ->
                        binding.tvName.text = selectedItem.nameItem
                    }

                dialogManager.showDialog()
            }
        }
    }
}